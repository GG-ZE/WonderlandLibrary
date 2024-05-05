/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  org.lwjgl.input.Keyboard
 */
package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.Proxy;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiAdd
extends GuiScreen {
    private final GuiAltManager prevGui;
    private GuiButton addButton;
    private GuiButton clipboardButton;
    private GuiTextField username;
    private GuiPasswordField password;
    private String status = "\u00a77Idle...";

    public GuiAdd(GuiAltManager gui) {
        this.prevGui = gui;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.addButton = new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, "Add");
        this.field_146292_n.add(this.addButton);
        this.clipboardButton = new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, "Clipboard");
        this.field_146292_n.add(this.clipboardButton);
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120, "Back"));
        this.username = new GuiTextField(2, (FontRenderer)Fonts.font40, this.field_146294_l / 2 - 100, 60, 200, 20);
        this.username.func_146195_b(true);
        this.username.func_146203_f(Integer.MAX_VALUE);
        this.password = new GuiPasswordField(3, Fonts.font40, this.field_146294_l / 2 - 100, 85, 200, 20);
        this.password.func_146203_f(Integer.MAX_VALUE);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_146278_c(0);
        Gui.func_73734_a((int)30, (int)30, (int)(this.field_146294_l - 30), (int)(this.field_146295_m - 30), (int)Integer.MIN_VALUE);
        this.func_73732_a(Fonts.font40, "Add Account", this.field_146294_l / 2, 34, 0xFFFFFF);
        this.func_73732_a(Fonts.font35, this.status == null ? "" : this.status, this.field_146294_l / 2, this.field_146295_m / 4 + 60, 0xFFFFFF);
        this.username.func_146194_f();
        this.password.func_146194_f();
        if (this.username.func_146179_b().isEmpty() && !this.username.func_146206_l()) {
            this.func_73732_a(Fonts.font40, "\u00a77Username / E-Mail", this.field_146294_l / 2 - 55, 66, 0xFFFFFF);
        }
        if (this.password.func_146179_b().isEmpty() && !this.password.func_146206_l()) {
            this.func_73732_a(Fonts.font40, "\u00a77Password", this.field_146294_l / 2 - 74, 91, 0xFFFFFF);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_146284_a(GuiButton button) throws IOException {
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0: {
                this.field_146297_k.func_147108_a((GuiScreen)this.prevGui);
                break;
            }
            case 1: {
                if (LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.stream().anyMatch(account -> account.getName().equals(this.username.func_146179_b()))) {
                    this.status = "\u00a7cThe account has already been added.";
                    break;
                }
                this.addAccount(this.username.func_146179_b(), this.password.func_146179_b());
                break;
            }
            case 2: {
                try {
                    String clipboardData = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    String[] accountData = clipboardData.split(":", 2);
                    if (!clipboardData.contains(":") || accountData.length != 2) {
                        this.status = "\u00a7cInvalid clipboard data. (Use: E-Mail:Password)";
                        return;
                    }
                    this.addAccount(accountData[0], accountData[1]);
                    break;
                }
                catch (UnsupportedFlavorException e) {
                    this.status = "\u00a7cClipboard flavor unsupported!";
                    ClientUtils.getLogger().error("Failed to read data from clipboard.", (Throwable)e);
                }
            }
        }
        super.func_146284_a(button);
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
            case 1: {
                this.field_146297_k.func_147108_a((GuiScreen)this.prevGui);
                return;
            }
            case 15: {
                TabUtils.tab(this.username, this.password);
                return;
            }
            case 28: {
                this.func_146284_a(this.addButton);
                return;
            }
        }
        if (this.username.func_146206_l()) {
            this.username.func_146201_a(typedChar, keyCode);
        }
        if (this.password.func_146206_l()) {
            this.password.func_146201_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.username.func_146192_a(mouseX, mouseY, mouseButton);
        this.password.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        this.username.func_146178_a();
        this.password.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        super.func_146281_b();
    }

    private void addAccount(String name, String password) {
        if (LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.stream().anyMatch(account -> account.getName().equals(name))) {
            this.status = "\u00a7cThe account has already been added.";
            return;
        }
        this.clipboardButton.field_146124_l = false;
        this.addButton.field_146124_l = false;
        MinecraftAccount account2 = new MinecraftAccount(name, password);
        new Thread(() -> {
            if (!account2.isCracked()) {
                this.status = "\u00a7aChecking...";
                try {
                    AltService.EnumAltService oldService = GuiAltManager.altService.getCurrentService();
                    if (oldService != AltService.EnumAltService.MOJANG) {
                        GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                    }
                    YggdrasilUserAuthentication userAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
                    userAuthentication.setUsername(account2.getName());
                    userAuthentication.setPassword(account2.getPassword());
                    userAuthentication.logIn();
                    account2.setAccountName(userAuthentication.getSelectedProfile().getName());
                    if (oldService == AltService.EnumAltService.THEALTENING) {
                        GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                    }
                }
                catch (AuthenticationException | IllegalAccessException | NoSuchFieldException | NullPointerException e) {
                    this.status = "\u00a7cThe account doesn't work.";
                    this.clipboardButton.field_146124_l = true;
                    this.addButton.field_146124_l = true;
                    return;
                }
            }
            LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.add(account2);
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
            this.prevGui.status = this.status = "\u00a7aThe account has been added.";
            this.field_146297_k.func_147108_a((GuiScreen)this.prevGui);
        }).start();
    }
}


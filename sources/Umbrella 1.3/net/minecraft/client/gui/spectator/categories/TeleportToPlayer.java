/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Ordering
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class TeleportToPlayer
implements ISpectatorMenuView,
ISpectatorMenuObject {
    private static final Ordering field_178674_a = Ordering.from((Comparator)new Comparator(){
        private static final String __OBFID = "CL_00001921";

        public int func_178746_a(NetworkPlayerInfo p_178746_1_, NetworkPlayerInfo p_178746_2_) {
            return ComparisonChain.start().compare((Comparable)p_178746_1_.func_178845_a().getId(), (Comparable)p_178746_2_.func_178845_a().getId()).result();
        }

        public int compare(Object p_compare_1_, Object p_compare_2_) {
            return this.func_178746_a((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
        }
    });
    private final List field_178673_b = Lists.newArrayList();
    private static final String __OBFID = "CL_00001922";

    public TeleportToPlayer() {
        this(field_178674_a.sortedCopy((Iterable)Minecraft.getMinecraft().getNetHandler().func_175106_d()));
    }

    public TeleportToPlayer(Collection p_i45493_1_) {
        for (NetworkPlayerInfo var3 : field_178674_a.sortedCopy((Iterable)p_i45493_1_)) {
            if (var3.getGameType() == WorldSettings.GameType.SPECTATOR) continue;
            this.field_178673_b.add(new PlayerMenuObject(var3.func_178845_a()));
        }
    }

    @Override
    public List func_178669_a() {
        return this.field_178673_b;
    }

    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText("Select a player to teleport to");
    }

    @Override
    public void func_178661_a(SpectatorMenu p_178661_1_) {
        p_178661_1_.func_178647_a(this);
    }

    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText("Teleport to player");
    }

    @Override
    public void func_178663_a(float p_178663_1_, int p_178663_2_) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }

    @Override
    public boolean func_178662_A_() {
        return !this.field_178673_b.isEmpty();
    }
}


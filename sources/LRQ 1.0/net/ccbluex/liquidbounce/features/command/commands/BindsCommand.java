/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.text.StringsKt
 *  org.lwjgl.input.Keyboard
 */
package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.lwjgl.input.Keyboard;

public final class BindsCommand
extends Command {
    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(String[] args) {
        void $this$filterTo$iv$iv;
        if (args.length > 1 && StringsKt.equals((String)args[1], (String)"clear", (boolean)true)) {
            for (Module module : LiquidBounce.INSTANCE.getModuleManager().getModules()) {
                module.setKeyBind(0);
            }
            this.chat("Removed all binds.");
            return;
        }
        this.chat("\u00a7c\u00a7lBinds");
        Iterable $this$filter$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!(it.getKeyBind() != 0)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Module it = (Module)element$iv;
            boolean bl = false;
            ClientUtils.displayChatMessage("\u00a76> \u00a7c" + it.getName() + ": \u00a7a\u00a7l" + Keyboard.getKeyName((int)it.getKeyBind()));
        }
        this.chatSyntax("binds clear");
    }

    public BindsCommand() {
        super("binds", new String[0]);
    }
}


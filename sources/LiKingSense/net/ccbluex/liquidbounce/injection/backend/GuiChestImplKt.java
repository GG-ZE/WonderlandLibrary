/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.inventory.GuiChest
 *  org.jetbrains.annotations.NotNull
 */
package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiChest;
import net.ccbluex.liquidbounce.injection.backend.GuiChestImpl;
import net.minecraft.client.gui.inventory.GuiChest;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\u0086\b\u00a8\u0006\u0004"}, d2={"unwrap", "Lnet/minecraft/client/gui/inventory/GuiChest;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiChest;", "wrap", "LiKingSense"})
public final class GuiChestImplKt {
    @NotNull
    public static final GuiChest unwrap(@NotNull IGuiChest $this$unwrap) {
        int $i$f$unwrap = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$unwrap, (String)"$this$unwrap");
        return (GuiChest)((GuiChestImpl)$this$unwrap).getWrapped();
    }

    @NotNull
    public static final IGuiChest wrap(@NotNull GuiChest $this$wrap) {
        int $i$f$wrap = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$wrap, (String)"$this$wrap");
        return new GuiChestImpl<GuiChest>($this$wrap);
    }
}


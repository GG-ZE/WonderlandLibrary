/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook;
import net.ccbluex.liquidbounce.injection.backend.SPacketPosLookImpl;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public final class SPacketPosLookImplKt {
    public static final SPacketPlayerPosLook unwrap(ISPacketPosLook $this$unwrap) {
        int $i$f$unwrap = 0;
        return (SPacketPlayerPosLook)((SPacketPosLookImpl)$this$unwrap).getWrapped();
    }

    public static final ISPacketPosLook wrap(SPacketPlayerPosLook $this$wrap) {
        int $i$f$wrap = 0;
        return new SPacketPosLookImpl<SPacketPlayerPosLook>($this$wrap);
    }
}


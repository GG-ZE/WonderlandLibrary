/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerPosLook;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public final class CPacketPlayerPosLookImpl
extends PacketImpl
implements ICPacketPlayerPosLook {
    public CPacketPlayerPosLookImpl(CPacketPlayer.PositionRotation positionRotation) {
        super((Packet)positionRotation);
    }
}


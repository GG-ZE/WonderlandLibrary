/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketAnimation;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;

public final class CPacketAnimationImpl<T extends CPacketAnimation>
extends PacketImpl<T>
implements ICPacketAnimation {
    public CPacketAnimationImpl(T wrapped) {
        super((Packet)wrapped);
    }
}


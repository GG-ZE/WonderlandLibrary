/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketClientStatus
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketClientStatus;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClientStatus;

public final class CPacketClientStatusImpl<T extends CPacketClientStatus>
extends PacketImpl<T>
implements ICPacketClientStatus {
    public CPacketClientStatusImpl(T wrapped) {
        super((Packet)wrapped);
    }
}


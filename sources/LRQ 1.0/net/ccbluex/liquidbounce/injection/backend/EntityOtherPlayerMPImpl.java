/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.injection.backend.EntityPlayerImpl;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public final class EntityOtherPlayerMPImpl<T extends EntityOtherPlayerMP>
extends EntityPlayerImpl<T>
implements IEntityOtherPlayerMP {
    public EntityOtherPlayerMPImpl(T wrapped) {
        super((EntityPlayer)wrapped);
    }
}


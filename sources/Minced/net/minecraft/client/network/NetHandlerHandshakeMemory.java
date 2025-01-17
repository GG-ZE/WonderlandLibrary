// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.network;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.INetHandler;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    
    public NetHandlerHandshakeMemory(final MinecraftServer mcServerIn, final NetworkManager networkManagerIn) {
        this.server = mcServerIn;
        this.networkManager = networkManagerIn;
    }
    
    @Override
    public void processHandshake(final C00Handshake packetIn) {
        this.networkManager.setConnectionState(packetIn.getRequestedState());
        this.networkManager.setNetHandler(new NetHandlerLoginServer(this.server, this.networkManager));
    }
    
    @Override
    public void onDisconnect(final ITextComponent reason) {
    }
}

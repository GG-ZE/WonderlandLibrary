package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class C18PacketSpectate
  implements Packet<INetHandler>
{
  private UUID field_179729_a;
  private static final String __OBFID = "CL_00002280";
  
  public C18PacketSpectate() {}
  
  public C18PacketSpectate(UUID p_i45932_1_)
  {
    this.field_179729_a = p_i45932_1_;
  }
  
  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    this.field_179729_a = data.readUuid();
  }
  
  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeUuid(this.field_179729_a);
  }
  
  public void func_179728_a(INetHandlerPlayServer p_179728_1_)
  {
    p_179728_1_.func_175088_a(this);
  }
  
  public Entity func_179727_a(WorldServer p_179727_1_)
  {
    return p_179727_1_.getEntityFromUuid(this.field_179729_a);
  }
  
  public void processPacket(INetHandler handler)
  {
    func_179728_a((INetHandlerPlayServer)handler);
  }
}

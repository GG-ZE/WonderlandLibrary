package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S13PacketDestroyEntities
  implements Packet<INetHandler>
{
  private int[] field_149100_a;
  private static final String __OBFID = "CL_00001320";
  
  public S13PacketDestroyEntities() {}
  
  public S13PacketDestroyEntities(int... p_i45211_1_)
  {
    this.field_149100_a = p_i45211_1_;
  }
  
  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    this.field_149100_a = new int[data.readVarIntFromBuffer()];
    for (int var2 = 0; var2 < this.field_149100_a.length; var2++) {
      this.field_149100_a[var2] = data.readVarIntFromBuffer();
    }
  }
  
  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(this.field_149100_a.length);
    for (int var2 = 0; var2 < this.field_149100_a.length; var2++) {
      data.writeVarIntToBuffer(this.field_149100_a[var2]);
    }
  }
  
  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleDestroyEntities(this);
  }
  
  public int[] func_149098_c()
  {
    return this.field_149100_a;
  }
  
  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}

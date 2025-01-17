package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S03PacketEnableCompression
  implements Packet<INetHandler>
{
  private int field_179733_a;
  private static final String __OBFID = "CL_00002279";
  
  public S03PacketEnableCompression() {}
  
  public S03PacketEnableCompression(int p_i45929_1_)
  {
    this.field_179733_a = p_i45929_1_;
  }
  
  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    this.field_179733_a = data.readVarIntFromBuffer();
  }
  
  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(this.field_179733_a);
  }
  
  public void func_179732_a(INetHandlerLoginClient p_179732_1_)
  {
    p_179732_1_.func_180464_a(this);
  }
  
  public int func_179731_a()
  {
    return this.field_179733_a;
  }
  
  public void processPacket(INetHandler handler)
  {
    func_179732_a((INetHandlerLoginClient)handler);
  }
}

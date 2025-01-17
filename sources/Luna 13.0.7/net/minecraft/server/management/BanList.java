package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class BanList
  extends UserList
{
  private static final String __OBFID = "CL_00001396";
  
  public BanList(File bansFile)
  {
    super(bansFile);
  }
  
  protected UserListEntry createEntry(JsonObject entryData)
  {
    return new IPBanEntry(entryData);
  }
  
  public boolean isBanned(SocketAddress address)
  {
    String var2 = addressToString(address);
    return hasEntry(var2);
  }
  
  public IPBanEntry getBanEntry(SocketAddress address)
  {
    String var2 = addressToString(address);
    return (IPBanEntry)getEntry(var2);
  }
  
  private String addressToString(SocketAddress address)
  {
    String var2 = address.toString();
    if (var2.contains("/")) {
      var2 = var2.substring(var2.indexOf('/') + 1);
    }
    if (var2.contains(":")) {
      var2 = var2.substring(0, var2.indexOf(':'));
    }
    return var2;
  }
}

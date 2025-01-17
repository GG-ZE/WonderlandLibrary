package net.minecraft.command;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitle.Type;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent.Serializer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle
  extends CommandBase
{
  private static final Logger field_175774_a = ;
  private static final String __OBFID = "CL_00002338";
  
  public CommandTitle() {}
  
  public String getCommandName()
  {
    return "title";
  }
  
  public int getRequiredPermissionLevel()
  {
    return 2;
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "orders.title.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args)
    throws CommandException
  {
    if (args.length < 2) {
      throw new WrongUsageException("orders.title.usage", new Object[0]);
    }
    if (args.length < 3)
    {
      if (("title".equals(args[1])) || ("subtitle".equals(args[1]))) {
        throw new WrongUsageException("orders.title.usage.title", new Object[0]);
      }
      if ("times".equals(args[1])) {
        throw new WrongUsageException("orders.title.usage.times", new Object[0]);
      }
    }
    EntityPlayerMP var3 = getPlayer(sender, args[0]);
    S45PacketTitle.Type var4 = S45PacketTitle.Type.func_179969_a(args[1]);
    if ((var4 != S45PacketTitle.Type.CLEAR) && (var4 != S45PacketTitle.Type.RESET))
    {
      if (var4 == S45PacketTitle.Type.TIMES)
      {
        if (args.length != 5) {
          throw new WrongUsageException("orders.title.usage", new Object[0]);
        }
        int var11 = parseInt(args[2]);
        int var12 = parseInt(args[3]);
        int var13 = parseInt(args[4]);
        S45PacketTitle var14 = new S45PacketTitle(var11, var12, var13);
        var3.playerNetServerHandler.sendPacket(var14);
        notifyOperators(sender, this, "orders.title.success", new Object[0]);
      }
      else
      {
        if (args.length < 3) {
          throw new WrongUsageException("orders.title.usage", new Object[0]);
        }
        String var10 = func_180529_a(args, 2);
        try
        {
          var6 = IChatComponent.Serializer.jsonToComponent(var10);
        }
        catch (JsonParseException var9)
        {
          IChatComponent var6;
          Throwable var8 = ExceptionUtils.getRootCause(var9);
          throw new SyntaxErrorException("orders.tellraw.jsonException", new Object[] { var8 == null ? "" : var8.getMessage() });
        }
        IChatComponent var6;
        S45PacketTitle var7 = new S45PacketTitle(var4, ChatComponentProcessor.func_179985_a(sender, var6, var3));
        var3.playerNetServerHandler.sendPacket(var7);
        notifyOperators(sender, this, "orders.title.success", new Object[0]);
      }
    }
    else
    {
      if (args.length != 2) {
        throw new WrongUsageException("orders.title.usage", new Object[0]);
      }
      S45PacketTitle var5 = new S45PacketTitle(var4, null);
      var3.playerNetServerHandler.sendPacket(var5);
      notifyOperators(sender, this, "orders.title.success", new Object[0]);
    }
  }
  
  public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
  {
    return args.length == 2 ? getListOfStringsMatchingLastWord(args, S45PacketTitle.Type.func_179971_a()) : args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
  }
  
  public boolean isUsernameIndex(String[] args, int index)
  {
    return index == 0;
  }
}

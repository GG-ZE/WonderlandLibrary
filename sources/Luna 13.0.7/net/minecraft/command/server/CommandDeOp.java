package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.BlockPos;

public class CommandDeOp
  extends CommandBase
{
  private static final String __OBFID = "CL_00000244";
  
  public CommandDeOp() {}
  
  public String getCommandName()
  {
    return "deop";
  }
  
  public int getRequiredPermissionLevel()
  {
    return 3;
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "orders.deop.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args)
    throws CommandException
  {
    if ((args.length == 1) && (args[0].length() > 0))
    {
      MinecraftServer var3 = MinecraftServer.getServer();
      GameProfile var4 = var3.getConfigurationManager().getOppedPlayers().getGameProfileFromName(args[0]);
      if (var4 == null) {
        throw new CommandException("orders.deop.failed", new Object[] { args[0] });
      }
      var3.getConfigurationManager().removeOp(var4);
      notifyOperators(sender, this, "orders.deop.success", new Object[] { args[0] });
    }
    else
    {
      throw new WrongUsageException("orders.deop.usage", new Object[0]);
    }
  }
  
  public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
  {
    return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames()) : null;
  }
}

package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.RegistryNamespaced;

public class CommandClearInventory
  extends CommandBase
{
  private static final String __OBFID = "CL_00000218";
  
  public CommandClearInventory() {}
  
  public String getCommandName()
  {
    return "clear";
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "orders.clear.usage";
  }
  
  public int getRequiredPermissionLevel()
  {
    return 2;
  }
  
  public void processCommand(ICommandSender sender, String[] args)
    throws CommandException
  {
    EntityPlayerMP var3 = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
    Item var4 = args.length >= 2 ? getItemByText(sender, args[1]) : null;
    int var5 = args.length >= 3 ? parseInt(args[2], -1) : -1;
    int var6 = args.length >= 4 ? parseInt(args[3], -1) : -1;
    NBTTagCompound var7 = null;
    if (args.length >= 5) {
      try
      {
        var7 = JsonToNBT.func_180713_a(func_180529_a(args, 4));
      }
      catch (NBTException var9)
      {
        throw new CommandException("orders.clear.tagError", new Object[] { var9.getMessage() });
      }
    }
    if ((args.length >= 2) && (var4 == null)) {
      throw new CommandException("orders.clear.failure", new Object[] { var3.getName() });
    }
    int var8 = var3.inventory.func_174925_a(var4, var5, var6, var7);
    var3.inventoryContainer.detectAndSendChanges();
    if (!var3.capabilities.isCreativeMode) {
      var3.updateHeldItem();
    }
    sender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, var8);
    if (var8 == 0) {
      throw new CommandException("orders.clear.failure", new Object[] { var3.getName() });
    }
    if (var6 == 0) {
      sender.addChatMessage(new ChatComponentTranslation("orders.clear.testing", new Object[] { var3.getName(), Integer.valueOf(var8) }));
    } else {
      notifyOperators(sender, this, "orders.clear.success", new Object[] { var3.getName(), Integer.valueOf(var8) });
    }
  }
  
  public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
  {
    return args.length == 2 ? func_175762_a(args, Item.itemRegistry.getKeys()) : args.length == 1 ? getListOfStringsMatchingLastWord(args, func_147209_d()) : null;
  }
  
  protected String[] func_147209_d()
  {
    return MinecraftServer.getServer().getAllUsernames();
  }
  
  public boolean isUsernameIndex(String[] args, int index)
  {
    return index == 0;
  }
}

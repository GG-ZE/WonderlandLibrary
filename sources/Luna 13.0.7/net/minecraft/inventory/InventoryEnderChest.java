package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest
  extends InventoryBasic
{
  private TileEntityEnderChest associatedChest;
  private static final String __OBFID = "CL_00001759";
  
  public InventoryEnderChest()
  {
    super("container.enderchest", false, 27);
  }
  
  public void setChestTileEntity(TileEntityEnderChest chestTileEntity)
  {
    this.associatedChest = chestTileEntity;
  }
  
  public void loadInventoryFromNBT(NBTTagList p_70486_1_)
  {
    for (int var2 = 0; var2 < getSizeInventory(); var2++) {
      setInventorySlotContents(var2, (ItemStack)null);
    }
    for (var2 = 0; var2 < p_70486_1_.tagCount(); var2++)
    {
      NBTTagCompound var3 = p_70486_1_.getCompoundTagAt(var2);
      int var4 = var3.getByte("Slot") & 0xFF;
      if ((var4 >= 0) && (var4 < getSizeInventory())) {
        setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
      }
    }
  }
  
  public NBTTagList saveInventoryToNBT()
  {
    NBTTagList var1 = new NBTTagList();
    for (int var2 = 0; var2 < getSizeInventory(); var2++)
    {
      ItemStack var3 = getStackInSlot(var2);
      if (var3 != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setByte("Slot", (byte)var2);
        var3.writeToNBT(var4);
        var1.appendTag(var4);
      }
    }
    return var1;
  }
  
  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return (this.associatedChest != null) && (!this.associatedChest.func_145971_a(playerIn)) ? false : super.isUseableByPlayer(playerIn);
  }
  
  public void openInventory(EntityPlayer playerIn)
  {
    if (this.associatedChest != null) {
      this.associatedChest.func_145969_a();
    }
    super.openInventory(playerIn);
  }
  
  public void closeInventory(EntityPlayer playerIn)
  {
    if (this.associatedChest != null) {
      this.associatedChest.func_145970_b();
    }
    super.closeInventory(playerIn);
    this.associatedChest = null;
  }
}

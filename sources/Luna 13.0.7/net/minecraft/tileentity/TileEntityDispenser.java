package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class TileEntityDispenser
  extends TileEntityLockable
  implements IInventory
{
  private static final Random field_174913_f = new Random();
  private ItemStack[] field_146022_i = new ItemStack[9];
  protected String field_146020_a;
  private static final String __OBFID = "CL_00000352";
  
  public TileEntityDispenser() {}
  
  public int getSizeInventory()
  {
    return 9;
  }
  
  public ItemStack getStackInSlot(int slotIn)
  {
    return this.field_146022_i[slotIn];
  }
  
  public ItemStack decrStackSize(int index, int count)
  {
    if (this.field_146022_i[index] != null)
    {
      if (this.field_146022_i[index].stackSize <= count)
      {
        ItemStack var3 = this.field_146022_i[index];
        this.field_146022_i[index] = null;
        markDirty();
        return var3;
      }
      ItemStack var3 = this.field_146022_i[index].splitStack(count);
      if (this.field_146022_i[index].stackSize == 0) {
        this.field_146022_i[index] = null;
      }
      markDirty();
      return var3;
    }
    return null;
  }
  
  public ItemStack getStackInSlotOnClosing(int index)
  {
    if (this.field_146022_i[index] != null)
    {
      ItemStack var2 = this.field_146022_i[index];
      this.field_146022_i[index] = null;
      return var2;
    }
    return null;
  }
  
  public int func_146017_i()
  {
    int var1 = -1;
    int var2 = 1;
    for (int var3 = 0; var3 < this.field_146022_i.length; var3++) {
      if ((this.field_146022_i[var3] != null) && (field_174913_f.nextInt(var2++) == 0)) {
        var1 = var3;
      }
    }
    return var1;
  }
  
  public void setInventorySlotContents(int index, ItemStack stack)
  {
    this.field_146022_i[index] = stack;
    if ((stack != null) && (stack.stackSize > getInventoryStackLimit())) {
      stack.stackSize = getInventoryStackLimit();
    }
    markDirty();
  }
  
  public int func_146019_a(ItemStack p_146019_1_)
  {
    for (int var2 = 0; var2 < this.field_146022_i.length; var2++) {
      if ((this.field_146022_i[var2] == null) || (this.field_146022_i[var2].getItem() == null))
      {
        setInventorySlotContents(var2, p_146019_1_);
        return var2;
      }
    }
    return -1;
  }
  
  public String getName()
  {
    return hasCustomName() ? this.field_146020_a : "container.dispenser";
  }
  
  public void func_146018_a(String p_146018_1_)
  {
    this.field_146020_a = p_146018_1_;
  }
  
  public boolean hasCustomName()
  {
    return this.field_146020_a != null;
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    NBTTagList var2 = compound.getTagList("Items", 10);
    this.field_146022_i = new ItemStack[getSizeInventory()];
    for (int var3 = 0; var3 < var2.tagCount(); var3++)
    {
      NBTTagCompound var4 = var2.getCompoundTagAt(var3);
      int var5 = var4.getByte("Slot") & 0xFF;
      if ((var5 >= 0) && (var5 < this.field_146022_i.length)) {
        this.field_146022_i[var5] = ItemStack.loadItemStackFromNBT(var4);
      }
    }
    if (compound.hasKey("CustomName", 8)) {
      this.field_146020_a = compound.getString("CustomName");
    }
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    super.writeToNBT(compound);
    NBTTagList var2 = new NBTTagList();
    for (int var3 = 0; var3 < this.field_146022_i.length; var3++) {
      if (this.field_146022_i[var3] != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setByte("Slot", (byte)var3);
        this.field_146022_i[var3].writeToNBT(var4);
        var2.appendTag(var4);
      }
    }
    compound.setTag("Items", var2);
    if (hasCustomName()) {
      compound.setString("CustomName", this.field_146020_a);
    }
  }
  
  public int getInventoryStackLimit()
  {
    return 64;
  }
  
  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return this.worldObj.getTileEntity(this.pos) == this;
  }
  
  public void openInventory(EntityPlayer playerIn) {}
  
  public void closeInventory(EntityPlayer playerIn) {}
  
  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return true;
  }
  
  public String getGuiID()
  {
    return "minecraft:dispenser";
  }
  
  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
  {
    return new ContainerDispenser(playerInventory, this);
  }
  
  public int getField(int id)
  {
    return 0;
  }
  
  public void setField(int id, int value) {}
  
  public int getFieldCount()
  {
    return 0;
  }
  
  public void clearInventory()
  {
    for (int var1 = 0; var1 < this.field_146022_i.length; var1++) {
      this.field_146022_i[var1] = null;
    }
  }
}

package net.minecraft.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity
{
  private static final Logger logger = ;
  private static Map nameToClassMap = Maps.newHashMap();
  private static Map classToNameMap = Maps.newHashMap();
  protected World worldObj;
  protected BlockPos pos;
  protected boolean tileEntityInvalid;
  private int blockMetadata;
  protected Block blockType;
  private static final String __OBFID = "CL_00000340";
  
  public TileEntity()
  {
    this.pos = BlockPos.ORIGIN;
    this.blockMetadata = -1;
  }
  
  private static void addMapping(Class cl, String id)
  {
    if (nameToClassMap.containsKey(id)) {
      throw new IllegalArgumentException("Duplicate id: " + id);
    }
    nameToClassMap.put(id, cl);
    classToNameMap.put(cl, id);
  }
  
  public World getWorld()
  {
    return this.worldObj;
  }
  
  public void setWorldObj(World worldIn)
  {
    this.worldObj = worldIn;
  }
  
  public boolean hasWorldObj()
  {
    return this.worldObj != null;
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    this.pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    String var2 = (String)classToNameMap.get(getClass());
    if (var2 == null) {
      throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
    }
    compound.setString("id", var2);
    compound.setInteger("x", this.pos.getX());
    compound.setInteger("y", this.pos.getY());
    compound.setInteger("z", this.pos.getZ());
  }
  
  public static TileEntity createAndLoadEntity(NBTTagCompound nbt)
  {
    TileEntity var1 = null;
    try
    {
      Class var2 = (Class)nameToClassMap.get(nbt.getString("id"));
      if (var2 != null) {
        var1 = (TileEntity)var2.newInstance();
      }
    }
    catch (Exception var3)
    {
      var3.printStackTrace();
    }
    if (var1 != null) {
      var1.readFromNBT(nbt);
    } else {
      logger.warn("Skipping BlockEntity with id " + nbt.getString("id"));
    }
    return var1;
  }
  
  public int getBlockMetadata()
  {
    if (this.blockMetadata == -1)
    {
      IBlockState var1 = this.worldObj.getBlockState(this.pos);
      this.blockMetadata = var1.getBlock().getMetaFromState(var1);
    }
    return this.blockMetadata;
  }
  
  public void markDirty()
  {
    if (this.worldObj != null)
    {
      IBlockState var1 = this.worldObj.getBlockState(this.pos);
      this.blockMetadata = var1.getBlock().getMetaFromState(var1);
      this.worldObj.func_175646_b(this.pos, this);
      if (getBlockType() != Blocks.air) {
        this.worldObj.updateComparatorOutputLevel(this.pos, getBlockType());
      }
    }
  }
  
  public double getDistanceSq(double p_145835_1_, double p_145835_3_, double p_145835_5_)
  {
    double var7 = this.pos.getX() + 0.5D - p_145835_1_;
    double var9 = this.pos.getY() + 0.5D - p_145835_3_;
    double var11 = this.pos.getZ() + 0.5D - p_145835_5_;
    return var7 * var7 + var9 * var9 + var11 * var11;
  }
  
  public double getMaxRenderDistanceSquared()
  {
    return 4096.0D;
  }
  
  public BlockPos getPos()
  {
    return this.pos;
  }
  
  public Block getBlockType()
  {
    if (this.blockType == null) {
      this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
    }
    return this.blockType;
  }
  
  public Packet<INetHandler> getDescriptionPacket()
  {
    return null;
  }
  
  public boolean isInvalid()
  {
    return this.tileEntityInvalid;
  }
  
  public void invalidate()
  {
    this.tileEntityInvalid = true;
  }
  
  public void validate()
  {
    this.tileEntityInvalid = false;
  }
  
  public boolean receiveClientEvent(int id, int type)
  {
    return false;
  }
  
  public void updateContainingBlockInfo()
  {
    this.blockType = null;
    this.blockMetadata = -1;
  }
  
  public void addInfoToCrashReport(CrashReportCategory reportCategory)
  {
    reportCategory.addCrashSectionCallable("Name", new Callable()
    {
      private static final String __OBFID = "CL_00000341";
      
      public String call()
      {
        return (String)TileEntity.classToNameMap.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
      }
    });
    if (this.worldObj != null)
    {
      CrashReportCategory.addBlockInfo(reportCategory, this.pos, getBlockType(), getBlockMetadata());
      reportCategory.addCrashSectionCallable("Actual block type", new Callable()
      {
        private static final String __OBFID = "CL_00000343";
        
        public String call()
        {
          int var1 = Block.getIdFromBlock(TileEntity.this.worldObj.getBlockState(TileEntity.this.pos).getBlock());
          try
          {
            return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(var1), Block.getBlockById(var1).getUnlocalizedName(), Block.getBlockById(var1).getClass().getCanonicalName() });
          }
          catch (Throwable var3) {}
          return "ID #" + var1;
        }
      });
      reportCategory.addCrashSectionCallable("Actual block data value", new Callable()
      {
        private static final String __OBFID = "CL_00000344";
        
        public String call()
        {
          IBlockState var1 = TileEntity.this.worldObj.getBlockState(TileEntity.this.pos);
          int var2 = var1.getBlock().getMetaFromState(var1);
          if (var2 < 0) {
            return "Unknown? (Got " + var2 + ")";
          }
          String var3 = String.format("%4s", new Object[] { Integer.toBinaryString(var2) }).replace(" ", "0");
          return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(var2), var3 });
        }
      });
    }
  }
  
  public void setPos(BlockPos posIn)
  {
    this.pos = posIn;
  }
  
  static
  {
    addMapping(TileEntityFurnace.class, "Furnace");
    addMapping(TileEntityChest.class, "Chest");
    addMapping(TileEntityEnderChest.class, "EnderChest");
    addMapping(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
    addMapping(TileEntityDispenser.class, "Trap");
    addMapping(TileEntityDropper.class, "Dropper");
    addMapping(TileEntitySign.class, "Sign");
    addMapping(TileEntityMobSpawner.class, "MobSpawner");
    addMapping(TileEntityNote.class, "Music");
    addMapping(TileEntityPiston.class, "Piston");
    addMapping(TileEntityBrewingStand.class, "Cauldron");
    addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
    addMapping(TileEntityEndPortal.class, "Airportal");
    addMapping(TileEntityCommandBlock.class, "Control");
    addMapping(TileEntityBeacon.class, "Beacon");
    addMapping(TileEntitySkull.class, "Skull");
    addMapping(TileEntityDaylightDetector.class, "DLDetector");
    addMapping(TileEntityHopper.class, "Hopper");
    addMapping(TileEntityComparator.class, "Comparator");
    addMapping(TileEntityFlowerPot.class, "FlowerPot");
    addMapping(TileEntityBanner.class, "Banner");
  }
}

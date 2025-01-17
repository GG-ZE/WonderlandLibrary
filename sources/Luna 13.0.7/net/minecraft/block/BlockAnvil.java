package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockAnvil
  extends BlockFalling
{
  public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
  public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
  private static final String __OBFID = "CL_00000192";
  
  protected BlockAnvil()
  {
    super(Material.anvil);
    setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DAMAGE, Integer.valueOf(0)));
    setLightOpacity(0);
    setCreativeTab(CreativeTabs.tabDecorations);
  }
  
  public boolean isFullCube()
  {
    return false;
  }
  
  public boolean isOpaqueCube()
  {
    return false;
  }
  
  public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
  {
    EnumFacing var9 = placer.func_174811_aO().rotateY();
    return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, var9).withProperty(DAMAGE, Integer.valueOf(meta >> 2));
  }
  
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
  {
    if (!worldIn.isRemote) {
      playerIn.displayGui(new Anvil(worldIn, pos));
    }
    return true;
  }
  
  public int damageDropped(IBlockState state)
  {
    return ((Integer)state.getValue(DAMAGE)).intValue();
  }
  
  public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
  {
    EnumFacing var3 = (EnumFacing)access.getBlockState(pos).getValue(FACING);
    if (var3.getAxis() == EnumFacing.Axis.X) {
      setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
    } else {
      setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
    }
  }
  
  public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
  {
    list.add(new ItemStack(itemIn, 1, 0));
    list.add(new ItemStack(itemIn, 1, 1));
    list.add(new ItemStack(itemIn, 1, 2));
  }
  
  protected void onStartFalling(EntityFallingBlock fallingEntity)
  {
    fallingEntity.setHurtEntities(true);
  }
  
  public void onEndFalling(World worldIn, BlockPos pos)
  {
    worldIn.playAuxSFX(1022, pos, 0);
  }
  
  public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
  {
    return true;
  }
  
  public IBlockState getStateForEntityRender(IBlockState state)
  {
    return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
  }
  
  public IBlockState getStateFromMeta(int meta)
  {
    return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 0x3)).withProperty(DAMAGE, Integer.valueOf((meta & 0xF) >> 2));
  }
  
  public int getMetaFromState(IBlockState state)
  {
    byte var2 = 0;
    int var3 = var2 | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
    var3 |= ((Integer)state.getValue(DAMAGE)).intValue() << 2;
    return var3;
  }
  
  protected BlockState createBlockState()
  {
    return new BlockState(this, new IProperty[] { FACING, DAMAGE });
  }
  
  public static class Anvil
    implements IInteractionObject
  {
    private final World world;
    private final BlockPos position;
    private static final String __OBFID = "CL_00002144";
    
    public Anvil(World worldIn, BlockPos pos)
    {
      this.world = worldIn;
      this.position = pos;
    }
    
    public String getName()
    {
      return "anvil";
    }
    
    public boolean hasCustomName()
    {
      return false;
    }
    
    public IChatComponent getDisplayName()
    {
      return new ChatComponentTranslation(Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
    }
    
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
      return new ContainerRepair(playerInventory, this.world, this.position, playerIn);
    }
    
    public String getGuiID()
    {
      return "minecraft:anvil";
    }
  }
}

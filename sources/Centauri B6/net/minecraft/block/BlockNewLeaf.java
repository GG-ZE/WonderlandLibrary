package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNewLeaf extends BlockLeaves {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
      public boolean apply(BlockPlanks.EnumType p_apply_1_) {
         return p_apply_1_.getMetadata() >= 4;
      }
   });

   public BlockNewLeaf() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
   }

   public BlockPlanks.EnumType getWoodType(int meta) {
      return BlockPlanks.EnumType.byMetadata((meta & 3) + 4);
   }

   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
      if(state.getValue(VARIANT) == BlockPlanks.EnumType.DARK_OAK && worldIn.rand.nextInt(chance) == 0) {
         spawnAsEntity(worldIn, pos, new ItemStack(Items.apple, 1, 0));
      }

   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      i = i | ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMetadata() - 4;
      if(!((Boolean)state.getValue(DECAYABLE)).booleanValue()) {
         i |= 4;
      }

      if(((Boolean)state.getValue(CHECK_DECAY)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public int getDamageValue(World worldIn, BlockPos pos) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      return iblockstate.getBlock().getMetaFromState(iblockstate) & 3;
   }

   public int damageDropped(IBlockState state) {
      return ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMetadata();
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(VARIANT, this.getWoodType(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
   }

   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
      if(!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
         player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMetadata() - 4));
      } else {
         super.harvestBlock(worldIn, player, pos, state, te);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT, CHECK_DECAY, DECAYABLE});
   }

   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
      list.add(new ItemStack(itemIn, 1, 0));
      list.add(new ItemStack(itemIn, 1, 1));
   }

   protected ItemStack createStackedBlock(IBlockState state) {
      return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMetadata() - 4);
   }
}

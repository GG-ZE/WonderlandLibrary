package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDurability
  extends Enchantment
{
  private static final String __OBFID = "CL_00000103";
  
  protected EnchantmentDurability(int p_i45773_1_, ResourceLocation p_i45773_2_, int p_i45773_3_)
  {
    super(p_i45773_1_, p_i45773_2_, p_i45773_3_, EnumEnchantmentType.BREAKABLE);
    setName("durability");
  }
  
  public int getMinEnchantability(int p_77321_1_)
  {
    return 5 + (p_77321_1_ - 1) * 8;
  }
  
  public int getMaxEnchantability(int p_77317_1_)
  {
    return super.getMinEnchantability(p_77317_1_) + 50;
  }
  
  public int getMaxLevel()
  {
    return 3;
  }
  
  public boolean canApply(ItemStack p_92089_1_)
  {
    return (p_92089_1_.isItemStackDamageable()) || (super.canApply(p_92089_1_));
  }
  
  public static boolean negateDamage(ItemStack p_92097_0_, int p_92097_1_, Random p_92097_2_)
  {
    return ((!(p_92097_0_.getItem() instanceof ItemArmor)) || (p_92097_2_.nextFloat() >= 0.6F)) && (p_92097_2_.nextInt(p_92097_1_ + 1) > 0);
  }
}

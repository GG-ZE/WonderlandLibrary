package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RandomPositionGenerator
{
  private static Vec3 staticVector = new Vec3(0.0D, 0.0D, 0.0D);
  private static final String __OBFID = "CL_00001629";
  
  public RandomPositionGenerator() {}
  
  public static Vec3 findRandomTarget(EntityCreature p_75463_0_, int p_75463_1_, int p_75463_2_)
  {
    return findRandomTargetBlock(p_75463_0_, p_75463_1_, p_75463_2_, (Vec3)null);
  }
  
  public static Vec3 findRandomTargetBlockTowards(EntityCreature p_75464_0_, int p_75464_1_, int p_75464_2_, Vec3 p_75464_3_)
  {
    staticVector = p_75464_3_.subtract(p_75464_0_.posX, p_75464_0_.posY, p_75464_0_.posZ);
    return findRandomTargetBlock(p_75464_0_, p_75464_1_, p_75464_2_, staticVector);
  }
  
  public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature p_75461_0_, int p_75461_1_, int p_75461_2_, Vec3 p_75461_3_)
  {
    staticVector = new Vec3(p_75461_0_.posX, p_75461_0_.posY, p_75461_0_.posZ).subtract(p_75461_3_);
    return findRandomTargetBlock(p_75461_0_, p_75461_1_, p_75461_2_, staticVector);
  }
  
  private static Vec3 findRandomTargetBlock(EntityCreature p_75462_0_, int p_75462_1_, int p_75462_2_, Vec3 p_75462_3_)
  {
    Random var4 = p_75462_0_.getRNG();
    boolean var5 = false;
    int var6 = 0;
    int var7 = 0;
    int var8 = 0;
    float var9 = -99999.0F;
    boolean var10;
    boolean var10;
    if (p_75462_0_.hasHome())
    {
      double var11 = p_75462_0_.func_180486_cf().distanceSq(MathHelper.floor_double(p_75462_0_.posX), MathHelper.floor_double(p_75462_0_.posY), MathHelper.floor_double(p_75462_0_.posZ)) + 4.0D;
      double var13 = p_75462_0_.getMaximumHomeDistance() + p_75462_1_;
      var10 = var11 < var13 * var13;
    }
    else
    {
      var10 = false;
    }
    for (int var17 = 0; var17 < 10; var17++)
    {
      int var12 = var4.nextInt(2 * p_75462_1_ + 1) - p_75462_1_;
      int var18 = var4.nextInt(2 * p_75462_2_ + 1) - p_75462_2_;
      int var14 = var4.nextInt(2 * p_75462_1_ + 1) - p_75462_1_;
      if ((p_75462_3_ == null) || (var12 * p_75462_3_.xCoord + var14 * p_75462_3_.zCoord >= 0.0D))
      {
        if ((p_75462_0_.hasHome()) && (p_75462_1_ > 1))
        {
          BlockPos var15 = p_75462_0_.func_180486_cf();
          if (p_75462_0_.posX > var15.getX()) {
            var12 -= var4.nextInt(p_75462_1_ / 2);
          } else {
            var12 += var4.nextInt(p_75462_1_ / 2);
          }
          if (p_75462_0_.posZ > var15.getZ()) {
            var14 -= var4.nextInt(p_75462_1_ / 2);
          } else {
            var14 += var4.nextInt(p_75462_1_ / 2);
          }
        }
        var12 += MathHelper.floor_double(p_75462_0_.posX);
        var18 += MathHelper.floor_double(p_75462_0_.posY);
        var14 += MathHelper.floor_double(p_75462_0_.posZ);
        BlockPos var15 = new BlockPos(var12, var18, var14);
        if ((!var10) || (p_75462_0_.func_180485_d(var15)))
        {
          float var16 = p_75462_0_.func_180484_a(var15);
          if (var16 > var9)
          {
            var9 = var16;
            var6 = var12;
            var7 = var18;
            var8 = var14;
            var5 = true;
          }
        }
      }
    }
    if (var5) {
      return new Vec3(var6, var7, var8);
    }
    return null;
  }
}

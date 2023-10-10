package net.minecraft.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob {
   public EntityMob(World worldIn) {
      super(worldIn);
      this.experienceValue = 5;
   }

   protected String getFallSoundString(int damageValue) {
      return damageValue > 4?"game.hostile.hurt.fall.big":"game.hostile.hurt.fall.small";
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
   }

   protected boolean canDropLoot() {
      return true;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      int i = 0;
      if(entityIn instanceof EntityLivingBase) {
         f += EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)entityIn).getCreatureAttribute());
         i += EnchantmentHelper.getKnockbackModifier(this);
      }

      boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
      if(flag) {
         if(i > 0) {
            entityIn.addVelocity((double)(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * (float)i * 0.5F));
            this.motionX *= 0.6D;
            this.motionZ *= 0.6D;
         }

         int j = EnchantmentHelper.getFireAspectModifier(this);
         if(j > 0) {
            entityIn.setFire(j * 4);
         }

         this.applyEnchantments(this, entityIn);
      }

      return flag;
   }

   protected String getDeathSound() {
      return "game.hostile.die";
   }

   protected String getHurtSound() {
      return "game.hostile.hurt";
   }

   protected String getSplashSound() {
      return "game.hostile.swim.splash";
   }

   public void onUpdate() {
      super.onUpdate();
      if(!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
         this.setDead();
      }

   }

   public void onLivingUpdate() {
      this.updateArmSwingProgress();
      float f = this.getBrightness(1.0F);
      if(f > 0.5F) {
         this.entityAge += 2;
      }

      super.onLivingUpdate();
   }

   protected String getSwimSound() {
      return "game.hostile.swim";
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if(this.isEntityInvulnerable(source)) {
         return false;
      } else if(!super.attackEntityFrom(source, amount)) {
         return false;
      } else {
         Entity entity = source.getEntity();
         return this.riddenByEntity != entity && this.ridingEntity != entity?true:true;
      }
   }

   public boolean getCanSpawnHere() {
      return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
   }

   public float getBlockPathWeight(BlockPos pos) {
      return 0.5F - this.worldObj.getLightBrightness(pos);
   }

   protected boolean isValidLightLevel() {
      BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
      if(this.worldObj.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
         return false;
      } else {
         int i = this.worldObj.getLightFromNeighbors(blockpos);
         if(this.worldObj.isThundering()) {
            int j = this.worldObj.getSkylightSubtracted();
            this.worldObj.setSkylightSubtracted(10);
            i = this.worldObj.getLightFromNeighbors(blockpos);
            this.worldObj.setSkylightSubtracted(j);
         }

         return i <= this.rand.nextInt(8);
      }
   }
}

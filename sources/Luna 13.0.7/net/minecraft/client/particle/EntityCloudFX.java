package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCloudFX
  extends EntityFX
{
  float field_70569_a;
  private static final String __OBFID = "CL_00000920";
  
  protected EntityCloudFX(World worldIn, double p_i1221_2_, double p_i1221_4_, double p_i1221_6_, double p_i1221_8_, double p_i1221_10_, double p_i1221_12_)
  {
    super(worldIn, p_i1221_2_, p_i1221_4_, p_i1221_6_, 0.0D, 0.0D, 0.0D);
    float var14 = 2.5F;
    this.motionX *= 0.10000000149011612D;
    this.motionY *= 0.10000000149011612D;
    this.motionZ *= 0.10000000149011612D;
    this.motionX += p_i1221_8_;
    this.motionY += p_i1221_10_;
    this.motionZ += p_i1221_12_;
    this.particleRed = (this.particleGreen = this.particleBlue = 1.0F - (float)(Math.random() * 0.30000001192092896D));
    this.particleScale *= 0.75F;
    this.particleScale *= var14;
    this.field_70569_a = this.particleScale;
    this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.3D)));
    this.particleMaxAge = ((int)(this.particleMaxAge * var14));
    this.noClip = false;
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = (this.particleAge + p_180434_3_) / this.particleMaxAge * 32.0F;
    var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
    this.particleScale = (this.field_70569_a * var9);
    super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
  }
  
  public void onUpdate()
  {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (this.particleAge++ >= this.particleMaxAge) {
      setDead();
    }
    setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
    moveEntity(this.motionX, this.motionY, this.motionZ);
    this.motionX *= 0.9599999785423279D;
    this.motionY *= 0.9599999785423279D;
    this.motionZ *= 0.9599999785423279D;
    EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 2.0D);
    if ((var1 != null) && (this.posY > var1.getEntityBoundingBox().minY))
    {
      this.posY += (var1.getEntityBoundingBox().minY - this.posY) * 0.2D;
      this.motionY += (var1.motionY - this.motionY) * 0.2D;
      setPosition(this.posX, this.posY, this.posZ);
    }
    if (this.onGround)
    {
      this.motionX *= 0.699999988079071D;
      this.motionZ *= 0.699999988079071D;
    }
  }
  
  public static class Factory
    implements IParticleFactory
  {
    private static final String __OBFID = "CL_00002591";
    
    public Factory() {}
    
    public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_)
    {
      return new EntityCloudFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
    }
  }
}

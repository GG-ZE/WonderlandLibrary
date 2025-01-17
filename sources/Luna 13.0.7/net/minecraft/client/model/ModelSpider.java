package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSpider
  extends ModelBase
{
  public ModelRenderer spiderHead;
  public ModelRenderer spiderNeck;
  public ModelRenderer spiderBody;
  public ModelRenderer spiderLeg1;
  public ModelRenderer spiderLeg2;
  public ModelRenderer spiderLeg3;
  public ModelRenderer spiderLeg4;
  public ModelRenderer spiderLeg5;
  public ModelRenderer spiderLeg6;
  public ModelRenderer spiderLeg7;
  public ModelRenderer spiderLeg8;
  private static final String __OBFID = "CL_00000860";
  
  public ModelSpider()
  {
    float var1 = 0.0F;
    byte var2 = 15;
    this.spiderHead = new ModelRenderer(this, 32, 4);
    this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, var1);
    this.spiderHead.setRotationPoint(0.0F, var2, -3.0F);
    this.spiderNeck = new ModelRenderer(this, 0, 0);
    this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
    this.spiderNeck.setRotationPoint(0.0F, var2, 0.0F);
    this.spiderBody = new ModelRenderer(this, 0, 12);
    this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
    this.spiderBody.setRotationPoint(0.0F, var2, 9.0F);
    this.spiderLeg1 = new ModelRenderer(this, 18, 0);
    this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg1.setRotationPoint(-4.0F, var2, 2.0F);
    this.spiderLeg2 = new ModelRenderer(this, 18, 0);
    this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg2.setRotationPoint(4.0F, var2, 2.0F);
    this.spiderLeg3 = new ModelRenderer(this, 18, 0);
    this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg3.setRotationPoint(-4.0F, var2, 1.0F);
    this.spiderLeg4 = new ModelRenderer(this, 18, 0);
    this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg4.setRotationPoint(4.0F, var2, 1.0F);
    this.spiderLeg5 = new ModelRenderer(this, 18, 0);
    this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg5.setRotationPoint(-4.0F, var2, 0.0F);
    this.spiderLeg6 = new ModelRenderer(this, 18, 0);
    this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg6.setRotationPoint(4.0F, var2, 0.0F);
    this.spiderLeg7 = new ModelRenderer(this, 18, 0);
    this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg7.setRotationPoint(-4.0F, var2, -1.0F);
    this.spiderLeg8 = new ModelRenderer(this, 18, 0);
    this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg8.setRotationPoint(4.0F, var2, -1.0F);
  }
  
  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    this.spiderHead.render(p_78088_7_);
    this.spiderNeck.render(p_78088_7_);
    this.spiderBody.render(p_78088_7_);
    this.spiderLeg1.render(p_78088_7_);
    this.spiderLeg2.render(p_78088_7_);
    this.spiderLeg3.render(p_78088_7_);
    this.spiderLeg4.render(p_78088_7_);
    this.spiderLeg5.render(p_78088_7_);
    this.spiderLeg6.render(p_78088_7_);
    this.spiderLeg7.render(p_78088_7_);
    this.spiderLeg8.render(p_78088_7_);
  }
  
  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    this.spiderHead.rotateAngleY = (p_78087_4_ / 57.295776F);
    this.spiderHead.rotateAngleX = (p_78087_5_ / 57.295776F);
    float var8 = 0.7853982F;
    this.spiderLeg1.rotateAngleZ = (-var8);
    this.spiderLeg2.rotateAngleZ = var8;
    this.spiderLeg3.rotateAngleZ = (-var8 * 0.74F);
    this.spiderLeg4.rotateAngleZ = (var8 * 0.74F);
    this.spiderLeg5.rotateAngleZ = (-var8 * 0.74F);
    this.spiderLeg6.rotateAngleZ = (var8 * 0.74F);
    this.spiderLeg7.rotateAngleZ = (-var8);
    this.spiderLeg8.rotateAngleZ = var8;
    float var9 = -0.0F;
    float var10 = 0.3926991F;
    this.spiderLeg1.rotateAngleY = (var10 * 2.0F + var9);
    this.spiderLeg2.rotateAngleY = (-var10 * 2.0F - var9);
    this.spiderLeg3.rotateAngleY = (var10 * 1.0F + var9);
    this.spiderLeg4.rotateAngleY = (-var10 * 1.0F - var9);
    this.spiderLeg5.rotateAngleY = (-var10 * 1.0F + var9);
    this.spiderLeg6.rotateAngleY = (var10 * 1.0F - var9);
    this.spiderLeg7.rotateAngleY = (-var10 * 2.0F + var9);
    this.spiderLeg8.rotateAngleY = (var10 * 2.0F - var9);
    float var11 = -(MathHelper.cos(p_78087_1_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_78087_2_;
    float var12 = -(MathHelper.cos(p_78087_1_ * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * p_78087_2_;
    float var13 = -(MathHelper.cos(p_78087_1_ * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * p_78087_2_;
    float var14 = -(MathHelper.cos(p_78087_1_ * 0.6662F * 2.0F + 4.712389F) * 0.4F) * p_78087_2_;
    float var15 = Math.abs(MathHelper.sin(p_78087_1_ * 0.6662F + 0.0F) * 0.4F) * p_78087_2_;
    float var16 = Math.abs(MathHelper.sin(p_78087_1_ * 0.6662F + 3.1415927F) * 0.4F) * p_78087_2_;
    float var17 = Math.abs(MathHelper.sin(p_78087_1_ * 0.6662F + 1.5707964F) * 0.4F) * p_78087_2_;
    float var18 = Math.abs(MathHelper.sin(p_78087_1_ * 0.6662F + 4.712389F) * 0.4F) * p_78087_2_;
    this.spiderLeg1.rotateAngleY += var11;
    this.spiderLeg2.rotateAngleY += -var11;
    this.spiderLeg3.rotateAngleY += var12;
    this.spiderLeg4.rotateAngleY += -var12;
    this.spiderLeg5.rotateAngleY += var13;
    this.spiderLeg6.rotateAngleY += -var13;
    this.spiderLeg7.rotateAngleY += var14;
    this.spiderLeg8.rotateAngleY += -var14;
    this.spiderLeg1.rotateAngleZ += var15;
    this.spiderLeg2.rotateAngleZ += -var15;
    this.spiderLeg3.rotateAngleZ += var16;
    this.spiderLeg4.rotateAngleZ += -var16;
    this.spiderLeg5.rotateAngleZ += var17;
    this.spiderLeg6.rotateAngleZ += -var17;
    this.spiderLeg7.rotateAngleZ += var18;
    this.spiderLeg8.rotateAngleZ += -var18;
  }
}

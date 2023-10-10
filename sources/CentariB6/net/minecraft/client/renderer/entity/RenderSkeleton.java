package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderSkeleton extends RenderBiped {
   private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public RenderSkeleton(RenderManager renderManagerIn) {
      super(renderManagerIn, new ModelSkeleton(), 0.5F);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerBipedArmor(this) {
         protected void initArmor() {
            this.field_177189_c = new ModelSkeleton(0.5F, true);
            this.field_177186_d = new ModelSkeleton(1.0F, true);
         }
      });
   }

   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(EntitySkeleton entitylivingbaseIn, float partialTickTime) {
      if(entitylivingbaseIn.getSkeletonType() == 1) {
         GlStateManager.scale(1.2F, 1.2F, 1.2F);
      }

   }

   protected ResourceLocation getEntityTexture(EntitySkeleton entity) {
      return entity.getSkeletonType() == 1?witherSkeletonTextures:skeletonTextures;
   }
}

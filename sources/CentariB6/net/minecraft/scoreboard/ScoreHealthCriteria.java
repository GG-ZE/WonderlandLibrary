package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreDummyCriteria;
import net.minecraft.scoreboard.IScoreObjectiveCriteria.EnumRenderType;
import net.minecraft.util.MathHelper;

public class ScoreHealthCriteria extends ScoreDummyCriteria {
   public ScoreHealthCriteria(String name) {
      super(name);
   }

   public boolean isReadOnly() {
      return true;
   }

   public EnumRenderType getRenderType() {
      return EnumRenderType.HEARTS;
   }

   public int func_96635_a(List p_96635_1_) {
      float f = 0.0F;

      for(EntityPlayer entityplayer : p_96635_1_) {
         f += entityplayer.getHealth() + entityplayer.getAbsorptionAmount();
      }

      if(p_96635_1_.size() > 0) {
         f /= (float)p_96635_1_.size();
      }

      return MathHelper.ceiling_float_int(f);
   }
}

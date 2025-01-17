package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityAIRestrictSun
  extends EntityAIBase
{
  private EntityCreature theEntity;
  private static final String __OBFID = "CL_00001611";
  
  public EntityAIRestrictSun(EntityCreature p_i1652_1_)
  {
    this.theEntity = p_i1652_1_;
  }
  
  public boolean shouldExecute()
  {
    return this.theEntity.worldObj.isDaytime();
  }
  
  public void startExecuting()
  {
    ((PathNavigateGround)this.theEntity.getNavigator()).func_179685_e(true);
  }
  
  public void resetTask()
  {
    ((PathNavigateGround)this.theEntity.getNavigator()).func_179685_e(false);
  }
}

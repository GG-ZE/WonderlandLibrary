package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;

public class EntityAIVillagerMate
  extends EntityAIBase
{
  private EntityVillager villagerObj;
  private EntityVillager mate;
  private World worldObj;
  private int matingTimeout;
  Village villageObj;
  private static final String __OBFID = "CL_00001594";
  
  public EntityAIVillagerMate(EntityVillager p_i1634_1_)
  {
    this.villagerObj = p_i1634_1_;
    this.worldObj = p_i1634_1_.worldObj;
    setMutexBits(3);
  }
  
  public boolean shouldExecute()
  {
    if (this.villagerObj.getGrowingAge() != 0) {
      return false;
    }
    if (this.villagerObj.getRNG().nextInt(500) != 0) {
      return false;
    }
    this.villageObj = this.worldObj.getVillageCollection().func_176056_a(new BlockPos(this.villagerObj), 0);
    if (this.villageObj == null) {
      return false;
    }
    if ((checkSufficientDoorsPresentForNewVillager()) && (this.villagerObj.func_175550_n(true)))
    {
      Entity var1 = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), this.villagerObj);
      if (var1 == null) {
        return false;
      }
      this.mate = ((EntityVillager)var1);
      return (this.mate.getGrowingAge() == 0) && (this.mate.func_175550_n(true));
    }
    return false;
  }
  
  public void startExecuting()
  {
    this.matingTimeout = 300;
    this.villagerObj.setMating(true);
  }
  
  public void resetTask()
  {
    this.villageObj = null;
    this.mate = null;
    this.villagerObj.setMating(false);
  }
  
  public boolean continueExecuting()
  {
    return (this.matingTimeout >= 0) && (checkSufficientDoorsPresentForNewVillager()) && (this.villagerObj.getGrowingAge() == 0) && (this.villagerObj.func_175550_n(false));
  }
  
  public void updateTask()
  {
    this.matingTimeout -= 1;
    this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);
    if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25D) {
      this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
    } else if ((this.matingTimeout == 0) && (this.mate.isMating())) {
      giveBirth();
    }
    if (this.villagerObj.getRNG().nextInt(35) == 0) {
      this.worldObj.setEntityState(this.villagerObj, (byte)12);
    }
  }
  
  private boolean checkSufficientDoorsPresentForNewVillager()
  {
    if (!this.villageObj.isMatingSeason()) {
      return false;
    }
    int var1 = (int)(this.villageObj.getNumVillageDoors() * 0.35D);
    return this.villageObj.getNumVillagers() < var1;
  }
  
  private void giveBirth()
  {
    EntityVillager var1 = this.villagerObj.func_180488_b(this.mate);
    this.mate.setGrowingAge(6000);
    this.villagerObj.setGrowingAge(6000);
    this.mate.func_175549_o(false);
    this.villagerObj.func_175549_o(false);
    var1.setGrowingAge(41536);
    var1.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
    this.worldObj.spawnEntityInWorld(var1);
    this.worldObj.setEntityState(var1, (byte)12);
  }
}

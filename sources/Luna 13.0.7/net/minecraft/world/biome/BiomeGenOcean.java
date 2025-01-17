package net.minecraft.world.biome;

import java.util.List;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenOcean
  extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000179";
  
  public BiomeGenOcean(int p_i1985_1_)
  {
    super(p_i1985_1_);
    this.spawnableCreatureList.clear();
  }
  
  public BiomeGenBase.TempCategory getTempCategory()
  {
    return BiomeGenBase.TempCategory.OCEAN;
  }
  
  public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
  {
    super.genTerrainBlocks(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
  }
}

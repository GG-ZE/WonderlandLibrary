package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettings.Factory;

public abstract class GenLayer
{
  private long worldGenSeed;
  protected GenLayer parent;
  private long chunkSeed;
  protected long baseSeed;
  private static final String __OBFID = "CL_00000559";
  
  public static GenLayer[] func_180781_a(long p_180781_0_, WorldType p_180781_2_, String p_180781_3_)
  {
    GenLayerIsland var4 = new GenLayerIsland(1L);
    GenLayerFuzzyZoom var13 = new GenLayerFuzzyZoom(2000L, var4);
    GenLayerAddIsland var14 = new GenLayerAddIsland(1L, var13);
    GenLayerZoom var15 = new GenLayerZoom(2001L, var14);
    var14 = new GenLayerAddIsland(2L, var15);
    var14 = new GenLayerAddIsland(50L, var14);
    var14 = new GenLayerAddIsland(70L, var14);
    GenLayerRemoveTooMuchOcean var16 = new GenLayerRemoveTooMuchOcean(2L, var14);
    GenLayerAddSnow var17 = new GenLayerAddSnow(2L, var16);
    var14 = new GenLayerAddIsland(3L, var17);
    GenLayerEdge var18 = new GenLayerEdge(2L, var14, GenLayerEdge.Mode.COOL_WARM);
    var18 = new GenLayerEdge(2L, var18, GenLayerEdge.Mode.HEAT_ICE);
    var18 = new GenLayerEdge(3L, var18, GenLayerEdge.Mode.SPECIAL);
    var15 = new GenLayerZoom(2002L, var18);
    var15 = new GenLayerZoom(2003L, var15);
    var14 = new GenLayerAddIsland(4L, var15);
    GenLayerAddMushroomIsland var19 = new GenLayerAddMushroomIsland(5L, var14);
    GenLayerDeepOcean var20 = new GenLayerDeepOcean(4L, var19);
    GenLayer var21 = GenLayerZoom.magnify(1000L, var20, 0);
    ChunkProviderSettings var5 = null;
    int var6 = 4;
    int var7 = var6;
    if ((p_180781_2_ == WorldType.CUSTOMIZED) && (p_180781_3_.length() > 0))
    {
      var5 = ChunkProviderSettings.Factory.func_177865_a(p_180781_3_).func_177864_b();
      var6 = var5.field_177780_G;
      var7 = var5.field_177788_H;
    }
    if (p_180781_2_ == WorldType.LARGE_BIOMES) {
      var6 = 6;
    }
    GenLayer var8 = GenLayerZoom.magnify(1000L, var21, 0);
    GenLayerRiverInit var22 = new GenLayerRiverInit(100L, var8);
    GenLayerBiome var9 = new GenLayerBiome(200L, var21, p_180781_2_, p_180781_3_);
    GenLayer var25 = GenLayerZoom.magnify(1000L, var9, 2);
    GenLayerBiomeEdge var26 = new GenLayerBiomeEdge(1000L, var25);
    GenLayer var10 = GenLayerZoom.magnify(1000L, var22, 2);
    GenLayerHills var27 = new GenLayerHills(1000L, var26, var10);
    var8 = GenLayerZoom.magnify(1000L, var22, 2);
    var8 = GenLayerZoom.magnify(1000L, var8, var7);
    GenLayerRiver var23 = new GenLayerRiver(1L, var8);
    GenLayerSmooth var24 = new GenLayerSmooth(1000L, var23);
    Object var28 = new GenLayerRareBiome(1001L, var27);
    for (int var11 = 0; var11 < var6; var11++)
    {
      var28 = new GenLayerZoom(1000 + var11, (GenLayer)var28);
      if (var11 == 0) {
        var28 = new GenLayerAddIsland(3L, (GenLayer)var28);
      }
      if ((var11 == 1) || (var6 == 1)) {
        var28 = new GenLayerShore(1000L, (GenLayer)var28);
      }
    }
    GenLayerSmooth var29 = new GenLayerSmooth(1000L, (GenLayer)var28);
    GenLayerRiverMix var30 = new GenLayerRiverMix(100L, var29, var24);
    GenLayerVoronoiZoom var12 = new GenLayerVoronoiZoom(10L, var30);
    var30.initWorldGenSeed(p_180781_0_);
    var12.initWorldGenSeed(p_180781_0_);
    return new GenLayer[] { var30, var12, var30 };
  }
  
  public GenLayer(long p_i2125_1_)
  {
    this.baseSeed = p_i2125_1_;
    this.baseSeed *= (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
    this.baseSeed += p_i2125_1_;
    this.baseSeed *= (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
    this.baseSeed += p_i2125_1_;
    this.baseSeed *= (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
    this.baseSeed += p_i2125_1_;
  }
  
  public void initWorldGenSeed(long p_75905_1_)
  {
    this.worldGenSeed = p_75905_1_;
    if (this.parent != null) {
      this.parent.initWorldGenSeed(p_75905_1_);
    }
    this.worldGenSeed *= (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
    this.worldGenSeed += this.baseSeed;
    this.worldGenSeed *= (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
    this.worldGenSeed += this.baseSeed;
    this.worldGenSeed *= (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
    this.worldGenSeed += this.baseSeed;
  }
  
  public void initChunkSeed(long p_75903_1_, long p_75903_3_)
  {
    this.chunkSeed = this.worldGenSeed;
    this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
    this.chunkSeed += p_75903_1_;
    this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
    this.chunkSeed += p_75903_3_;
    this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
    this.chunkSeed += p_75903_1_;
    this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
    this.chunkSeed += p_75903_3_;
  }
  
  protected int nextInt(int p_75902_1_)
  {
    int var2 = (int)((this.chunkSeed >> 24) % p_75902_1_);
    if (var2 < 0) {
      var2 += p_75902_1_;
    }
    this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
    this.chunkSeed += this.worldGenSeed;
    return var2;
  }
  
  public abstract int[] getInts(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  protected static boolean biomesEqualOrMesaPlateau(int biomeIDA, int biomeIDB)
  {
    if (biomeIDA == biomeIDB) {
      return true;
    }
    if ((biomeIDA != BiomeGenBase.mesaPlateau_F.biomeID) && (biomeIDA != BiomeGenBase.mesaPlateau.biomeID))
    {
      BiomeGenBase var2 = BiomeGenBase.getBiome(biomeIDA);
      BiomeGenBase var3 = BiomeGenBase.getBiome(biomeIDB);
      try
      {
        return (var2 != null) && (var3 != null) ? var2.isEqualTo(var3) : false;
      }
      catch (Throwable var7)
      {
        CrashReport var5 = CrashReport.makeCrashReport(var7, "Comparing biomes");
        CrashReportCategory var6 = var5.makeCategory("Biomes being compared");
        var6.addCrashSection("Biome A ID", Integer.valueOf(biomeIDA));
        var6.addCrashSection("Biome B ID", Integer.valueOf(biomeIDB));
        var6.addCrashSectionCallable("Biome A", new Callable()
        {
          private static final String __OBFID = "CL_00000560";
          
          public String call()
          {
            return String.valueOf(this.val$var2);
          }
        });
        var6.addCrashSectionCallable("Biome B", new Callable()
        {
          private static final String __OBFID = "CL_00000561";
          
          public String call()
          {
            return String.valueOf(this.val$var3);
          }
        });
        throw new ReportedException(var5);
      }
    }
    return (biomeIDB == BiomeGenBase.mesaPlateau_F.biomeID) || (biomeIDB == BiomeGenBase.mesaPlateau.biomeID);
  }
  
  protected static boolean isBiomeOceanic(int p_151618_0_)
  {
    return (p_151618_0_ == BiomeGenBase.ocean.biomeID) || (p_151618_0_ == BiomeGenBase.deepOcean.biomeID) || (p_151618_0_ == BiomeGenBase.frozenOcean.biomeID);
  }
  
  protected int selectRandom(int... p_151619_1_)
  {
    return p_151619_1_[nextInt(p_151619_1_.length)];
  }
  
  protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_)
  {
    return (p_151617_3_ == p_151617_4_) && (p_151617_1_ != p_151617_2_) ? p_151617_3_ : (p_151617_2_ == p_151617_4_) && (p_151617_1_ != p_151617_3_) ? p_151617_2_ : (p_151617_2_ == p_151617_3_) && (p_151617_1_ != p_151617_4_) ? p_151617_2_ : (p_151617_1_ == p_151617_4_) && (p_151617_2_ != p_151617_3_) ? p_151617_1_ : (p_151617_1_ == p_151617_3_) && (p_151617_2_ != p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_2_) && (p_151617_3_ != p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_3_) && (p_151617_1_ == p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_2_) && (p_151617_1_ == p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_2_) && (p_151617_1_ == p_151617_3_) ? p_151617_1_ : (p_151617_2_ == p_151617_3_) && (p_151617_3_ == p_151617_4_) ? p_151617_2_ : selectRandom(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ });
  }
}

package net.minecraft.client.gui.achievement;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Mouse;

public class GuiAchievements
  extends GuiScreen
  implements IProgressMeter
{
  private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
  private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
  private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
  private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
  private static final ResourceLocation field_146561_C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
  protected GuiScreen parentScreen;
  protected int field_146555_f = 256;
  protected int field_146557_g = 202;
  protected int field_146563_h;
  protected int field_146564_i;
  protected float field_146570_r = 1.0F;
  protected double field_146569_s;
  protected double field_146568_t;
  protected double field_146567_u;
  protected double field_146566_v;
  protected double field_146565_w;
  protected double field_146573_x;
  private int field_146554_D;
  private StatFileWriter statFileWriter;
  private boolean loadingAchievements = true;
  private static final String __OBFID = "CL_00000722";
  
  public GuiAchievements(GuiScreen p_i45026_1_, StatFileWriter p_i45026_2_)
  {
    this.parentScreen = p_i45026_1_;
    this.statFileWriter = p_i45026_2_;
    short var3 = 141;
    short var4 = 141;
    this.field_146569_s = (this.field_146567_u = this.field_146565_w = AchievementList.openInventory.displayColumn * 24 - var3 / 2 - 12);
    this.field_146568_t = (this.field_146566_v = this.field_146573_x = AchievementList.openInventory.displayRow * 24 - var4 / 2);
  }
  
  public void initGui()
  {
    Minecraft.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
    this.buttonList.clear();
    this.buttonList.add(new GuiOptionButton(1, width / 2 + 24, height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (!this.loadingAchievements) {
      if (button.id == 1) {
        this.mc.displayGuiScreen(this.parentScreen);
      }
    }
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
    {
      this.mc.displayGuiScreen(null);
      this.mc.setIngameFocus();
    }
    else
    {
      super.keyTyped(typedChar, keyCode);
    }
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    if (this.loadingAchievements)
    {
      drawDefaultBackground();
      drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2, height / 2, 16777215);
      drawCenteredString(this.fontRendererObj, lanSearchStates[((int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length))], width / 2, height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
    }
    else
    {
      if (Mouse.isButtonDown(0))
      {
        int var4 = (width - this.field_146555_f) / 2;
        int var5 = (height - this.field_146557_g) / 2;
        int var6 = var4 + 8;
        int var7 = var5 + 17;
        if (((this.field_146554_D == 0) || (this.field_146554_D == 1)) && (mouseX >= var6) && (mouseX < var6 + 224) && (mouseY >= var7) && (mouseY < var7 + 155))
        {
          if (this.field_146554_D == 0)
          {
            this.field_146554_D = 1;
          }
          else
          {
            this.field_146567_u -= (mouseX - this.field_146563_h) * this.field_146570_r;
            this.field_146566_v -= (mouseY - this.field_146564_i) * this.field_146570_r;
            this.field_146565_w = (this.field_146569_s = this.field_146567_u);
            this.field_146573_x = (this.field_146568_t = this.field_146566_v);
          }
          this.field_146563_h = mouseX;
          this.field_146564_i = mouseY;
        }
      }
      else
      {
        this.field_146554_D = 0;
      }
      int var4 = Mouse.getDWheel();
      float var11 = this.field_146570_r;
      if (var4 < 0) {
        this.field_146570_r += 0.25F;
      } else if (var4 > 0) {
        this.field_146570_r -= 0.25F;
      }
      this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
      if (this.field_146570_r != var11)
      {
        float var10000 = var11 - this.field_146570_r;
        float var12 = var11 * this.field_146555_f;
        float var8 = var11 * this.field_146557_g;
        float var9 = this.field_146570_r * this.field_146555_f;
        float var10 = this.field_146570_r * this.field_146557_g;
        this.field_146567_u -= (var9 - var12) * 0.5F;
        this.field_146566_v -= (var10 - var8) * 0.5F;
        this.field_146565_w = (this.field_146569_s = this.field_146567_u);
        this.field_146573_x = (this.field_146568_t = this.field_146566_v);
      }
      if (this.field_146565_w < field_146572_y) {
        this.field_146565_w = field_146572_y;
      }
      if (this.field_146573_x < field_146571_z) {
        this.field_146573_x = field_146571_z;
      }
      if (this.field_146565_w >= field_146559_A) {
        this.field_146565_w = (field_146559_A - 1);
      }
      if (this.field_146573_x >= field_146560_B) {
        this.field_146573_x = (field_146560_B - 1);
      }
      drawDefaultBackground();
      drawAchievementScreen(mouseX, mouseY, partialTicks);
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      drawTitle();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
    }
  }
  
  public void doneLoading()
  {
    if (this.loadingAchievements) {
      this.loadingAchievements = false;
    }
  }
  
  public void updateScreen()
  {
    if (!this.loadingAchievements)
    {
      this.field_146569_s = this.field_146567_u;
      this.field_146568_t = this.field_146566_v;
      double var1 = this.field_146565_w - this.field_146567_u;
      double var3 = this.field_146573_x - this.field_146566_v;
      if (var1 * var1 + var3 * var3 < 4.0D)
      {
        this.field_146567_u += var1;
        this.field_146566_v += var3;
      }
      else
      {
        this.field_146567_u += var1 * 0.85D;
        this.field_146566_v += var3 * 0.85D;
      }
    }
  }
  
  protected void drawTitle()
  {
    int var1 = (width - this.field_146555_f) / 2;
    int var2 = (height - this.field_146557_g) / 2;
    this.fontRendererObj.drawString(I18n.format("gui.achievements", new Object[0]), var1 + 15, var2 + 5, 4210752);
  }
  
  protected void drawAchievementScreen(int p_146552_1_, int p_146552_2_, float p_146552_3_)
  {
    int var4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * p_146552_3_);
    int var5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * p_146552_3_);
    if (var4 < field_146572_y) {
      var4 = field_146572_y;
    }
    if (var5 < field_146571_z) {
      var5 = field_146571_z;
    }
    if (var4 >= field_146559_A) {
      var4 = field_146559_A - 1;
    }
    if (var5 >= field_146560_B) {
      var5 = field_146560_B - 1;
    }
    int var6 = (width - this.field_146555_f) / 2;
    int var7 = (height - this.field_146557_g) / 2;
    int var8 = var6 + 16;
    int var9 = var7 + 17;
    this.zLevel = 0.0F;
    GlStateManager.depthFunc(518);
    GlStateManager.pushMatrix();
    GlStateManager.translate(var8, var9, -200.0F);
    GlStateManager.scale(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
    GlStateManager.func_179098_w();
    GlStateManager.disableLighting();
    GlStateManager.enableRescaleNormal();
    GlStateManager.enableColorMaterial();
    int var10 = var4 + 288 >> 4;
    int var11 = var5 + 288 >> 4;
    int var12 = (var4 + 288) % 16;
    int var13 = (var5 + 288) % 16;
    boolean var14 = true;
    boolean var15 = true;
    boolean var16 = true;
    boolean var17 = true;
    boolean var18 = true;
    Random var19 = new Random();
    float var20 = 16.0F / this.field_146570_r;
    float var21 = 16.0F / this.field_146570_r;
    for (int var22 = 0; var22 * var20 - var13 < 155.0F; var22++)
    {
      float var23 = 0.6F - (var11 + var22) / 25.0F * 0.3F;
      GlStateManager.color(var23, var23, var23, 1.0F);
      for (int var24 = 0; var24 * var21 - var12 < 224.0F; var24++)
      {
        var19.setSeed(this.mc.getSession().getPlayerID().hashCode() + var10 + var24 + (var11 + var22) * 16);
        int var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
        TextureAtlasSprite var26 = func_175371_a(Blocks.sand);
        if ((var25 <= 37) && (var11 + var22 != 35))
        {
          if (var25 == 22)
          {
            if (var19.nextInt(2) == 0) {
              var26 = func_175371_a(Blocks.diamond_ore);
            } else {
              var26 = func_175371_a(Blocks.redstone_ore);
            }
          }
          else if (var25 == 10) {
            var26 = func_175371_a(Blocks.iron_ore);
          } else if (var25 == 8) {
            var26 = func_175371_a(Blocks.coal_ore);
          } else if (var25 > 4) {
            var26 = func_175371_a(Blocks.stone);
          } else if (var25 > 0) {
            var26 = func_175371_a(Blocks.dirt);
          }
        }
        else
        {
          Block var27 = Blocks.bedrock;
          var26 = func_175371_a(var27);
        }
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        func_175175_a(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
      }
    }
    GlStateManager.enableDepth();
    GlStateManager.depthFunc(515);
    this.mc.getTextureManager().bindTexture(field_146561_C);
    for (var22 = 0; var22 < AchievementList.achievementList.size(); var22++)
    {
      Achievement var34 = (Achievement)AchievementList.achievementList.get(var22);
      if (var34.parentAchievement != null)
      {
        int var24 = var34.displayColumn * 24 - var4 + 11;
        int var25 = var34.displayRow * 24 - var5 + 11;
        int var37 = var34.parentAchievement.displayColumn * 24 - var4 + 11;
        int var40 = var34.parentAchievement.displayRow * 24 - var5 + 11;
        boolean var28 = this.statFileWriter.hasAchievementUnlocked(var34);
        boolean var29 = this.statFileWriter.canUnlockAchievement(var34);
        int var30 = this.statFileWriter.func_150874_c(var34);
        if (var30 <= 4)
        {
          int var31 = -16777216;
          if (var28) {
            var31 = -6250336;
          } else if (var29) {
            var31 = -16711936;
          }
          drawHorizontalLine(var24, var37, var25, var31);
          drawVerticalLine(var37, var25, var40, var31);
          if (var24 > var37) {
            drawTexturedModalRect(var24 - 11 - 7, var25 - 5, 114, 234, 7, 11);
          } else if (var24 < var37) {
            drawTexturedModalRect(var24 + 11, var25 - 5, 107, 234, 7, 11);
          } else if (var25 > var40) {
            drawTexturedModalRect(var24 - 5, var25 - 11 - 7, 96, 234, 11, 7);
          } else if (var25 < var40) {
            drawTexturedModalRect(var24 - 5, var25 + 11, 96, 241, 11, 7);
          }
        }
      }
    }
    Achievement var33 = null;
    float var23 = (p_146552_1_ - var8) * this.field_146570_r;
    float var35 = (p_146552_2_ - var9) * this.field_146570_r;
    RenderHelper.enableGUIStandardItemLighting();
    GlStateManager.disableLighting();
    GlStateManager.enableRescaleNormal();
    GlStateManager.enableColorMaterial();
    for (int var25 = 0; var25 < AchievementList.achievementList.size(); var25++)
    {
      Achievement var38 = (Achievement)AchievementList.achievementList.get(var25);
      int var40 = var38.displayColumn * 24 - var4;
      int var41 = var38.displayRow * 24 - var5;
      if ((var40 >= -24) && (var41 >= -24) && (var40 <= 224.0F * this.field_146570_r) && (var41 <= 155.0F * this.field_146570_r))
      {
        int var42 = this.statFileWriter.func_150874_c(var38);
        float var43;
        if (this.statFileWriter.hasAchievementUnlocked(var38))
        {
          float var43 = 0.75F;
          GlStateManager.color(var43, var43, var43, 1.0F);
        }
        else if (this.statFileWriter.canUnlockAchievement(var38))
        {
          float var43 = 1.0F;
          GlStateManager.color(var43, var43, var43, 1.0F);
        }
        else if (var42 < 3)
        {
          float var43 = 0.3F;
          GlStateManager.color(var43, var43, var43, 1.0F);
        }
        else if (var42 == 3)
        {
          float var43 = 0.2F;
          GlStateManager.color(var43, var43, var43, 1.0F);
        }
        else
        {
          if (var42 != 4) {
            continue;
          }
          var43 = 0.1F;
          GlStateManager.color(var43, var43, var43, 1.0F);
        }
        this.mc.getTextureManager().bindTexture(field_146561_C);
        if (var38.getSpecial()) {
          drawTexturedModalRect(var40 - 2, var41 - 2, 26, 202, 26, 26);
        } else {
          drawTexturedModalRect(var40 - 2, var41 - 2, 0, 202, 26, 26);
        }
        if (!this.statFileWriter.canUnlockAchievement(var38))
        {
          var43 = 0.1F;
          GlStateManager.color(var43, var43, var43, 1.0F);
          this.itemRender.func_175039_a(false);
        }
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        this.itemRender.func_180450_b(var38.theItemStack, var40 + 3, var41 + 3);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableLighting();
        if (!this.statFileWriter.canUnlockAchievement(var38)) {
          this.itemRender.func_175039_a(true);
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if ((var23 >= var40) && (var23 <= var40 + 22) && (var35 >= var41) && (var35 <= var41 + 22)) {
          var33 = var38;
        }
      }
    }
    GlStateManager.disableDepth();
    GlStateManager.enableBlend();
    GlStateManager.popMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(field_146561_C);
    drawTexturedModalRect(var6, var7, 0, 0, this.field_146555_f, this.field_146557_g);
    this.zLevel = 0.0F;
    GlStateManager.depthFunc(515);
    GlStateManager.disableDepth();
    GlStateManager.func_179098_w();
    super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);
    if (var33 != null)
    {
      String var36 = var33.getStatName().getUnformattedText();
      String var39 = var33.getDescription();
      int var40 = p_146552_1_ + 12;
      int var41 = p_146552_2_ - 4;
      int var42 = this.statFileWriter.func_150874_c(var33);
      if (this.statFileWriter.canUnlockAchievement(var33))
      {
        int var30 = Math.max(this.fontRendererObj.getStringWidth(var36), 120);
        int var31 = this.fontRendererObj.splitStringWidth(var39, var30);
        if (this.statFileWriter.hasAchievementUnlocked(var33)) {
          var31 += 12;
        }
        drawGradientRect(var40 - 3, var41 - 3, var40 + var30 + 3, var41 + var31 + 3 + 12, -1073741824, -1073741824);
        this.fontRendererObj.drawSplitString(var39, var40, var41 + 12, var30, -6250336);
        if (this.statFileWriter.hasAchievementUnlocked(var33)) {
          this.fontRendererObj.func_175063_a(I18n.format("achievement.taken", new Object[0]), var40, var41 + var31 + 4, -7302913);
        }
      }
      else if (var42 == 3)
      {
        var36 = I18n.format("achievement.unknown", new Object[0]);
        int var30 = Math.max(this.fontRendererObj.getStringWidth(var36), 120);
        String var44 = new ChatComponentTranslation("achievement.requires", new Object[] { var33.parentAchievement.getStatName() }).getUnformattedText();
        int var32 = this.fontRendererObj.splitStringWidth(var44, var30);
        drawGradientRect(var40 - 3, var41 - 3, var40 + var30 + 3, var41 + var32 + 12 + 3, -1073741824, -1073741824);
        this.fontRendererObj.drawSplitString(var44, var40, var41 + 12, var30, -9416624);
      }
      else if (var42 < 3)
      {
        int var30 = Math.max(this.fontRendererObj.getStringWidth(var36), 120);
        String var44 = new ChatComponentTranslation("achievement.requires", new Object[] { var33.parentAchievement.getStatName() }).getUnformattedText();
        int var32 = this.fontRendererObj.splitStringWidth(var44, var30);
        drawGradientRect(var40 - 3, var41 - 3, var40 + var30 + 3, var41 + var32 + 12 + 3, -1073741824, -1073741824);
        this.fontRendererObj.drawSplitString(var44, var40, var41 + 12, var30, -9416624);
      }
      else
      {
        var36 = null;
      }
      if (var36 != null) {
        this.fontRendererObj.func_175063_a(var36, var40, var41, var33.getSpecial() ? -8355776 : this.statFileWriter.canUnlockAchievement(var33) ? -1 : var33.getSpecial() ? -128 : -8355712);
      }
    }
    GlStateManager.enableDepth();
    GlStateManager.enableLighting();
    RenderHelper.disableStandardItemLighting();
  }
  
  private TextureAtlasSprite func_175371_a(Block p_175371_1_)
  {
    return Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178122_a(p_175371_1_.getDefaultState());
  }
  
  public boolean doesGuiPauseGame()
  {
    return !this.loadingAchievements;
  }
}

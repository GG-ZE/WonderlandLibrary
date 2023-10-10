package net.minecraft.client.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiRenameWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback {
   private static final Logger logger = LogManager.getLogger();
   private final DateFormat field_146633_h = new SimpleDateFormat();
   protected GuiScreen parentScreen;
   protected String field_146628_f = "Select world";
   private boolean field_146634_i;
   private int field_146640_r;
   private List field_146639_s;
   private net.minecraft.client.gui.GuiSelectWorld.List field_146638_t;
   private String field_146637_u;
   private String field_146636_v;
   private String[] field_146635_w = new String[4];
   private boolean field_146643_x;
   private GuiButton deleteButton;
   private GuiButton selectButton;
   private GuiButton renameButton;
   private GuiButton recreateButton;

   public GuiSelectWorld(GuiScreen parentScreenIn) {
      this.parentScreen = parentScreenIn;
   }

   // $FF: synthetic method
   static int access$100(GuiSelectWorld x0) {
      return x0.field_146640_r;
   }

   // $FF: synthetic method
   static GuiButton access$200(GuiSelectWorld x0) {
      return x0.selectButton;
   }

   // $FF: synthetic method
   static GuiButton access$300(GuiSelectWorld x0) {
      return x0.deleteButton;
   }

   // $FF: synthetic method
   static List access$000(GuiSelectWorld x0) {
      return x0.field_146639_s;
   }

   // $FF: synthetic method
   static GuiButton access$400(GuiSelectWorld x0) {
      return x0.renameButton;
   }

   // $FF: synthetic method
   static GuiButton access$500(GuiSelectWorld x0) {
      return x0.recreateButton;
   }

   // $FF: synthetic method
   static String access$600(GuiSelectWorld x0) {
      return x0.field_146637_u;
   }

   // $FF: synthetic method
   static int access$102(GuiSelectWorld x0, int x1) {
      return x0.field_146640_r = x1;
   }

   // $FF: synthetic method
   static DateFormat access$700(GuiSelectWorld x0) {
      return x0.field_146633_h;
   }

   // $FF: synthetic method
   static String access$800(GuiSelectWorld x0) {
      return x0.field_146636_v;
   }

   // $FF: synthetic method
   static String[] access$900(GuiSelectWorld x0) {
      return x0.field_146635_w;
   }

   protected void actionPerformed(GuiButton button) throws IOException {
      if(button.enabled) {
         if(button.id == 2) {
            String s = this.func_146614_d(this.field_146640_r);
            if(s != null) {
               this.field_146643_x = true;
               GuiYesNo guiyesno = func_152129_a(this, s, this.field_146640_r);
               this.mc.displayGuiScreen(guiyesno);
            }
         } else if(button.id == 1) {
            this.func_146615_e(this.field_146640_r);
         } else if(button.id == 3) {
            this.mc.displayGuiScreen(new GuiCreateWorld(this));
         } else if(button.id == 6) {
            this.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
         } else if(button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if(button.id == 7) {
            GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
            ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), false);
            WorldInfo worldinfo = isavehandler.loadWorldInfo();
            isavehandler.flush();
            guicreateworld.func_146318_a(worldinfo);
            this.mc.displayGuiScreen(guicreateworld);
         } else {
            this.field_146638_t.actionPerformed(button);
         }
      }

   }

   public static GuiYesNo func_152129_a(GuiYesNoCallback p_152129_0_, String p_152129_1_, int p_152129_2_) {
      String s = I18n.format("selectWorld.deleteQuestion", new Object[0]);
      String s1 = "\'" + p_152129_1_ + "\' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
      String s2 = I18n.format("selectWorld.deleteButton", new Object[0]);
      String s3 = I18n.format("gui.cancel", new Object[0]);
      GuiYesNo guiyesno = new GuiYesNo(p_152129_0_, s, s1, s2, s3, p_152129_2_);
      return guiyesno;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.field_146638_t.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.field_146628_f, this.width / 2, 20, 16777215);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146638_t.handleMouseInput();
   }

   public void confirmClicked(boolean result, int id) {
      if(this.field_146643_x) {
         this.field_146643_x = false;
         if(result) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory(this.func_146621_a(id));

            try {
               this.func_146627_h();
            } catch (AnvilConverterException var5) {
               logger.error("Couldn\'t load level list", var5);
            }
         }

         this.mc.displayGuiScreen(this);
      }

   }

   public void initGui() {
      this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);

      try {
         this.func_146627_h();
      } catch (AnvilConverterException var2) {
         logger.error("Couldn\'t load level list", var2);
         this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
         return;
      }

      this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
      this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
      this.field_146635_w[GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
      this.field_146635_w[GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
      this.field_146635_w[GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
      this.field_146635_w[GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
      this.field_146638_t = new net.minecraft.client.gui.GuiSelectWorld.List(this, this.mc);
      this.field_146638_t.registerScrollButtons(4, 5);
      this.func_146618_g();
   }

   public void func_146618_g() {
      this.buttonList.add(this.selectButton = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
      this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
      this.buttonList.add(this.renameButton = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
      this.buttonList.add(this.deleteButton = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
      this.buttonList.add(this.recreateButton = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
      this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
      this.selectButton.enabled = false;
      this.deleteButton.enabled = false;
      this.renameButton.enabled = false;
      this.recreateButton.enabled = false;
   }

   protected String func_146614_d(int p_146614_1_) {
      String s = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();
      if(StringUtils.isEmpty(s)) {
         s = I18n.format("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
      }

      return s;
   }

   private void func_146627_h() throws AnvilConverterException {
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      this.field_146639_s = isaveformat.getSaveList();
      Collections.sort(this.field_146639_s);
      this.field_146640_r = -1;
   }

   protected String func_146621_a(int p_146621_1_) {
      return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
   }

   public void func_146615_e(int p_146615_1_) {
      this.mc.displayGuiScreen((GuiScreen)null);
      if(!this.field_146634_i) {
         this.field_146634_i = true;
         String s = this.func_146621_a(p_146615_1_);
         if(s == null) {
            s = "World" + p_146615_1_;
         }

         String s1 = this.func_146614_d(p_146615_1_);
         if(s1 == null) {
            s1 = "World" + p_146615_1_;
         }

         if(this.mc.getSaveLoader().canLoadWorld(s)) {
            this.mc.launchIntegratedServer(s, s1, (WorldSettings)null);
         }
      }

   }
}

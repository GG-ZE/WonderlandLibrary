package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink
  extends GuiYesNo
{
  private final String openLinkWarning;
  private final String copyLinkButtonText;
  private final String linkText;
  private boolean showSecurityWarning = true;
  private static final String __OBFID = "CL_00000683";
  
  public GuiConfirmOpenLink(GuiYesNoCallback p_i1084_1_, String p_i1084_2_, int p_i1084_3_, boolean p_i1084_4_)
  {
    super(p_i1084_1_, I18n.format(p_i1084_4_ ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), p_i1084_2_, p_i1084_3_);
    this.confirmButtonText = I18n.format(p_i1084_4_ ? "chat.link.open" : "gui.yes", new Object[0]);
    this.cancelButtonText = I18n.format(p_i1084_4_ ? "gui.cancel" : "gui.no", new Object[0]);
    this.copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
    this.openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
    this.linkText = p_i1084_2_;
  }
  
  public void initGui()
  {
    this.buttonList.add(new GuiButton(0, width / 2 - 50 - 105, height / 6 + 96, 100, 20, this.confirmButtonText));
    this.buttonList.add(new GuiButton(2, width / 2 - 50, height / 6 + 96, 100, 20, this.copyLinkButtonText));
    this.buttonList.add(new GuiButton(1, width / 2 - 50 + 105, height / 6 + 96, 100, 20, this.cancelButtonText));
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (button.id == 2) {
      copyLinkToClipboard();
    }
    this.parentScreen.confirmClicked(button.id == 0, this.parentButtonClickedId);
  }
  
  public void copyLinkToClipboard()
  {
    setClipboardString(this.linkText);
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    super.drawScreen(mouseX, mouseY, partialTicks);
    if (this.showSecurityWarning) {
      drawCenteredString(this.fontRendererObj, this.openLinkWarning, width / 2, 110, 16764108);
    }
  }
  
  public void disableSecurityWarning()
  {
    this.showSecurityWarning = false;
  }
}

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.otcV2.SideGui;

import net.minecraft.client.Minecraft;

public abstract class GuiPanel {
    final Minecraft mc = Minecraft.func_71410_x();
    public float rectWidth;
    public float rectHeight;

    public abstract void initGui();

    public abstract void keyTyped(char var1, int var2);

    public abstract void drawScreen(int var1, int var2, float var3, int var4);

    public abstract void mouseClicked(int var1, int var2, int var3);

    public abstract void mouseReleased(int var1, int var2, int var3);
}


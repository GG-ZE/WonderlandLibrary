/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 */
package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.utils.render.ColorUtil;
import net.ccbluex.liquidbounce.utils.render.ShaderUtil;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GradientUtil {
    private static final ShaderUtil gradientMaskShader = new ShaderUtil("shaders/gradientMask.frag");
    private static final ShaderUtil gradientShader = new ShaderUtil("shaders/gradient.frag");

    public static void drawGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        ScaledResolution sr = new ScaledResolution(Stencil.mc);
        GlStateManager.func_179117_G();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        gradientShader.init();
        gradientShader.setUniformf("location", x * (float)sr.func_78325_e(), (float)Minecraft.func_71410_x().field_71440_d - height * (float)sr.func_78325_e() - y * (float)sr.func_78325_e());
        gradientShader.setUniformf("rectSize", width * (float)sr.func_78325_e(), height * (float)sr.func_78325_e());
        gradientShader.setUniformf("alpha", alpha);
        gradientShader.setUniformf("color1", (float)bottomLeft.getRed() / 255.0f, (float)bottomLeft.getGreen() / 255.0f, (float)bottomLeft.getBlue() / 255.0f);
        gradientShader.setUniformf("color2", (float)topLeft.getRed() / 255.0f, (float)topLeft.getGreen() / 255.0f, (float)topLeft.getBlue() / 255.0f);
        gradientShader.setUniformf("color3", (float)bottomRight.getRed() / 255.0f, (float)bottomRight.getGreen() / 255.0f, (float)bottomRight.getBlue() / 255.0f);
        gradientShader.setUniformf("color4", (float)topRight.getRed() / 255.0f, (float)topRight.getGreen() / 255.0f, (float)topRight.getBlue() / 255.0f);
        ShaderUtil.drawQuads(x, y, width, height);
        gradientShader.unload();
        GlStateManager.func_179084_k();
    }

    public static void drawGradientLR(float x, float y, float width, float height, float alpha, Color left, Color right) {
        GradientUtil.drawGradient(x, y, width, height, alpha, left, left, right, right);
    }

    public static void drawGradientTB(float x, float y, float width, float height, float alpha, Color top, Color bottom) {
        GradientUtil.drawGradient(x, y, width, height, alpha, bottom, top, bottom, top);
    }

    public static void applyGradientHorizontal(float x, float y, float width, float height, float alpha, Color left, Color right, Runnable content) {
        GradientUtil.applyGradient(x, y, width, height, alpha, left, left, right, right, content);
    }

    public static void applyGradientVertical(float x, float y, float width, float height, float alpha, Color top, Color bottom, Runnable content) {
        GradientUtil.applyGradient(x, y, width, height, alpha, bottom, top, bottom, top, content);
    }

    public static void applyGradientCornerRL(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topRight, Runnable content) {
        Color mixedColor = ColorUtil.interpolateColorC(topRight, bottomLeft, 0.5f);
        GradientUtil.applyGradient(x, y, width, height, alpha, bottomLeft, mixedColor, mixedColor, topRight, content);
    }

    public static void applyGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight, Runnable content) {
        GlStateManager.func_179117_G();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        gradientMaskShader.init();
        ScaledResolution sr = new ScaledResolution(Stencil.mc);
        gradientMaskShader.setUniformf("location", x * (float)sr.func_78325_e(), (float)Minecraft.func_71410_x().field_71440_d - height * (float)sr.func_78325_e() - y * (float)sr.func_78325_e());
        gradientMaskShader.setUniformf("rectSize", width * (float)sr.func_78325_e(), height * (float)sr.func_78325_e());
        gradientMaskShader.setUniformf("alpha", alpha);
        gradientMaskShader.setUniformi("tex", 0);
        gradientMaskShader.setUniformf("color1", (float)bottomLeft.getRed() / 255.0f, (float)bottomLeft.getGreen() / 255.0f, (float)bottomLeft.getBlue() / 255.0f);
        gradientMaskShader.setUniformf("color2", (float)topLeft.getRed() / 255.0f, (float)topLeft.getGreen() / 255.0f, (float)topLeft.getBlue() / 255.0f);
        gradientMaskShader.setUniformf("color3", (float)bottomRight.getRed() / 255.0f, (float)bottomRight.getGreen() / 255.0f, (float)bottomRight.getBlue() / 255.0f);
        gradientMaskShader.setUniformf("color4", (float)topRight.getRed() / 255.0f, (float)topRight.getGreen() / 255.0f, (float)topRight.getBlue() / 255.0f);
        content.run();
        gradientMaskShader.unload();
        GlStateManager.func_179084_k();
    }
}


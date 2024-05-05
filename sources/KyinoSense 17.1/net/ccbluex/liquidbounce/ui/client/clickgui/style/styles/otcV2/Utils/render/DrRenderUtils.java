/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  org.lwjgl.opengl.GL11
 */
package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.otcV2.Utils.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.otcV2.Utils.animations.Animation;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.otcV2.Utils.normal.Utils;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.otcV2.Utils.render.GLUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class DrRenderUtils
implements Utils {
    public static float zLevel;

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), 0.0).func_181673_a((double)(u * f), (double)((v + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 0.0).func_181673_a((double)((u + width) * f), (double)((v + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, 0.0).func_181673_a((double)((u + width) * f), (double)(v * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, 0.0).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawGradientRect2(double x, double y, double width, double height, int startColor, int endColor) {
        DrRenderUtils.drawGradientRect(x, y, x + width, y + height, startColor, endColor);
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return DrRenderUtils.fadeTo(startColour, endColour, progress);
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0 - progress;
        int r = (int)((double)(startColour >> 16 & 0xFF) * invert + (double)(endColour >> 16 & 0xFF) * progress);
        int g = (int)((double)(startColour >> 8 & 0xFF) * invert + (double)(endColour >> 8 & 0xFF) * progress);
        int b = (int)((double)(startColour & 0xFF) * invert + (double)(endColour & 0xFF) * progress);
        int a = (int)((double)(startColour >> 24 & 0xFF) * invert + (double)(endColour >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(right, top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b(left, top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b(left, bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }

    public static void drawGradientRectSideways2(double x, double y, double width, double height, int startColor, int endColor) {
        DrRenderUtils.drawGradientRectSideways(x, y, x + width, y + height, startColor, endColor);
    }

    public static void drawGradientRectSideways(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(right, top, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        worldrenderer.func_181662_b(left, top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b(left, bottom, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }

    public static void scissor(double x, double y, double width, double height) {
        ScaledResolution sr = new ScaledResolution(mc);
        double scale = sr.func_78325_e();
        double finalHeight = height * scale;
        double finalY = ((double)sr.func_78328_b() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;
        GL11.glScissor((int)((int)finalX), (int)((int)(finalY - finalHeight)), (int)((int)finalWidth), (int)((int)finalHeight));
    }

    public static int interpolateColor(int color1, int color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        Color cColor1 = new Color(color1);
        Color cColor2 = new Color(color2);
        return DrRenderUtils.interpolateColorC(cColor1, cColor2, amount).getRGB();
    }

    public static void renderRoundedRect(float x, float y, float width, float height, float radius, int color) {
        DrRenderUtils.drawGoodCircle(x + radius, y + radius, radius, color);
        DrRenderUtils.drawGoodCircle(x + width - radius, y + radius, radius, color);
        DrRenderUtils.drawGoodCircle(x + radius, y + height - radius, radius, color);
        DrRenderUtils.drawGoodCircle(x + width - radius, y + height - radius, radius, color);
        DrRenderUtils.drawRect2(x + radius, y, width - radius * 2.0f, height, color);
        DrRenderUtils.drawRect2(x, y + radius, width, height - radius * 2.0f, color);
    }

    public static Color darker(Color color, float FACTOR) {
        return new Color(Math.max((int)((float)color.getRed() * FACTOR), 0), Math.max((int)((float)color.getGreen() * FACTOR), 0), Math.max((int)((float)color.getBlue() * FACTOR), 0), color.getAlpha());
    }

    public static void drawClickGuiArrow(float x, float y, float size, Animation animation, int color) {
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GLUtil.setup2DRendering(() -> GLUtil.render(5, () -> {
            DrRenderUtils.color(color);
            double interpolation = DrRenderUtils.interpolate(0.0, (double)size / 2.0, animation.getOutput());
            if (animation.getOutput() >= 0.48) {
                GL11.glVertex2d((double)(size / 2.0f), (double)DrRenderUtils.interpolate((double)size / 2.0, 0.0, animation.getOutput()));
            }
            GL11.glVertex2d((double)0.0, (double)interpolation);
            if (animation.getOutput() < 0.48) {
                GL11.glVertex2d((double)(size / 2.0f), (double)DrRenderUtils.interpolate((double)size / 2.0, 0.0, animation.getOutput()));
            }
            GL11.glVertex2d((double)size, (double)interpolation);
        }));
        GL11.glTranslatef((float)(-x), (float)(-y), (float)0.0f);
    }

    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        return new Color(DrRenderUtils.interpolateInt(color1.getRed(), color2.getRed(), amount), DrRenderUtils.interpolateInt(color1.getGreen(), color2.getGreen(), amount), DrRenderUtils.interpolateInt(color1.getBlue(), color2.getBlue(), amount), DrRenderUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return DrRenderUtils.interpolate(oldValue, newValue, (float)interpolationValue).intValue();
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return oldValue + (newValue - oldValue) * interpolationValue;
    }

    public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseY >= y && (float)mouseX < x + width && (float)mouseY < y + height;
    }

    public static Color applyOpacity(Color color, float opacity) {
        opacity = Math.min(1.0f, Math.max(0.0f, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)((float)color.getAlpha() * opacity));
    }

    public static int applyOpacity(int color, float opacity) {
        Color old = new Color(color);
        return DrRenderUtils.applyOpacity(old, opacity).getRGB();
    }

    public static void drawGoodCircle(double x, double y, float radius, int color) {
        DrRenderUtils.color(color);
        GLUtil.setup2DRendering(() -> {
            GL11.glEnable((int)2832);
            GL11.glHint((int)3153, (int)4354);
            GL11.glPointSize((float)(radius * (float)(2 * DrRenderUtils.mc.field_71474_y.field_74335_Z)));
            GLUtil.render(0, () -> GL11.glVertex2d((double)x, (double)y));
        });
    }

    public static double animate(double endPoint, double current, double speed) {
        boolean shouldContinueAnimation;
        boolean bl = shouldContinueAnimation = endPoint > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        double factor = dif * speed;
        return current + (shouldContinueAnimation ? factor : -factor);
    }

    public static void fakeCircleGlow(float posX, float posY, float radius, Color color, float maxAlpha) {
        DrRenderUtils.setAlphaLimit(0.0f);
        GL11.glShadeModel((int)7425);
        GLUtil.setup2DRendering(() -> GLUtil.render(6, () -> {
            DrRenderUtils.color(color.getRGB(), maxAlpha);
            GL11.glVertex2d((double)posX, (double)posY);
            DrRenderUtils.color(color.getRGB(), 0.0f);
            for (int i = 0; i <= 100; ++i) {
                double angle = (double)i * 0.06283 + 3.1415;
                double x2 = Math.sin(angle) * (double)radius;
                double y2 = Math.cos(angle) * (double)radius;
                GL11.glVertex2d((double)((double)posX + x2), (double)((double)posY + y2));
            }
        }));
        GL11.glShadeModel((int)7424);
        DrRenderUtils.setAlphaLimit(1.0f);
    }

    public static Color brighter(Color color, float FACTOR) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();
        int i = (int)(1.0 / (1.0 - (double)FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) {
            r = i;
        }
        if (g > 0 && g < i) {
            g = i;
        }
        if (b > 0 && b < i) {
            b = i;
        }
        return new Color(Math.min((int)((float)r / FACTOR), 255), Math.min((int)((float)g / FACTOR), 255), Math.min((int)((float)b / FACTOR), 255), alpha);
    }

    public static void scale(float x, float y, float scale, Runnable data) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)1.0f);
        GL11.glTranslatef((float)(-x), (float)(-y), (float)0.0f);
        data.run();
        GL11.glPopMatrix();
    }

    public static void resetColor() {
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        DrRenderUtils.resetColor();
        GLUtil.setup2DRendering(() -> GLUtil.render(7, () -> {
            DrRenderUtils.color(color);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glVertex2d((double)x, (double)(y + height));
            GL11.glVertex2d((double)(x + width), (double)(y + height));
            GL11.glVertex2d((double)(x + width), (double)y);
        }));
    }

    public static void color(int color, float alpha) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)r, (float)g, (float)b, (float)alpha);
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.func_179141_d();
        GlStateManager.func_179092_a((int)516, (float)((float)((double)limit * 0.01)));
    }

    public static void color(int color) {
        DrRenderUtils.color(color, (float)(color >> 24 & 0xFF) / 255.0f);
    }
}


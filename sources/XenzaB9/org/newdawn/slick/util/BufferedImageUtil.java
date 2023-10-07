// 
// Decompiled by Procyon v0.6.0
// 

package org.newdawn.slick.util;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.ImageIOImageData;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import java.awt.image.BufferedImage;

public class BufferedImageUtil
{
    public static Texture getTexture(final String resourceName, final BufferedImage resourceImage) throws IOException {
        final Texture tex = getTexture(resourceName, resourceImage, 3553, 32856, 9729, 9729);
        return tex;
    }
    
    public static Texture getTexture(final String resourceName, final BufferedImage resourceImage, final int filter) throws IOException {
        final Texture tex = getTexture(resourceName, resourceImage, 3553, 32856, filter, filter);
        return tex;
    }
    
    public static Texture getTexture(final String resourceName, final BufferedImage resourceimage, final int target, final int dstPixelFormat, final int minFilter, final int magFilter) throws IOException {
        final ImageIOImageData data = new ImageIOImageData();
        int srcPixelFormat = 0;
        final int textureID = InternalTextureLoader.createTextureID();
        final TextureImpl texture = new TextureImpl(resourceName, target, textureID);
        GL11.glEnable(3553);
        GL11.glBindTexture(target, textureID);
        final BufferedImage bufferedImage = resourceimage;
        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());
        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = 6408;
        }
        else {
            srcPixelFormat = 6407;
        }
        final ByteBuffer textureBuffer = data.imageToByteBuffer(bufferedImage, false, false, null);
        texture.setTextureHeight(data.getTexHeight());
        texture.setTextureWidth(data.getTexWidth());
        texture.setAlpha(data.getDepth() == 32);
        if (target == 3553) {
            GL11.glTexParameteri(target, 10241, minFilter);
            GL11.glTexParameteri(target, 10240, magFilter);
            if (GLContext.getCapabilities().GL_EXT_texture_mirror_clamp) {
                GL11.glTexParameteri(3553, 10242, 34627);
                GL11.glTexParameteri(3553, 10243, 34627);
            }
            else {
                GL11.glTexParameteri(3553, 10242, 10496);
                GL11.glTexParameteri(3553, 10243, 10496);
            }
        }
        GL11.glTexImage2D(target, 0, dstPixelFormat, texture.getTextureWidth(), texture.getTextureHeight(), 0, srcPixelFormat, 5121, textureBuffer);
        return texture;
    }
    
    private static void copyArea(final BufferedImage image, final int x, final int y, final int width, final int height, final int dx, final int dy) {
        final Graphics2D g = (Graphics2D)image.getGraphics();
        g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
    }
}

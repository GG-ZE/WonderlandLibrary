package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredTexture extends AbstractTexture {
   private static final Logger logger = LogManager.getLogger();
   public final List layeredTextureNames;

   public void loadTexture(IResourceManager var1) throws IOException {
      this.deleteGlTexture();
      BufferedImage var2 = null;

      try {
         Iterator var4 = this.layeredTextureNames.iterator();

         while(var4.hasNext()) {
            String var3 = (String)var4.next();
            if (var3 != null) {
               InputStream var5 = var1.getResource(new ResourceLocation(var3)).getInputStream();
               BufferedImage var6 = TextureUtil.readBufferedImage(var5);
               if (var2 == null) {
                  var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
               }

               var2.getGraphics().drawImage(var6, 0, 0, (ImageObserver)null);
            }
         }
      } catch (IOException var7) {
         logger.error((String)"Couldn't load layered image", (Throwable)var7);
         return;
      }

      TextureUtil.uploadTextureImage(this.getGlTextureId(), var2);
   }

   public LayeredTexture(String... var1) {
      this.layeredTextureNames = Lists.newArrayList((Object[])var1);
   }
}

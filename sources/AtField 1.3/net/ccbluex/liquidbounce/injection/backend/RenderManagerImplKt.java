/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderManager
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.injection.backend.RenderManagerImpl;
import net.minecraft.client.renderer.entity.RenderManager;

public final class RenderManagerImplKt {
    public static final IRenderManager wrap(RenderManager renderManager) {
        boolean bl = false;
        return new RenderManagerImpl(renderManager);
    }

    public static final RenderManager unwrap(IRenderManager iRenderManager) {
        boolean bl = false;
        return ((RenderManagerImpl)iRenderManager).getWrapped();
    }
}


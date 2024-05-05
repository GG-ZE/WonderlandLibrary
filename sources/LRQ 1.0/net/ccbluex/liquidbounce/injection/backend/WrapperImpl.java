/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ccbluex.liquidbounce.injection.backend;

import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.Wrapper;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl;
import net.ccbluex.liquidbounce.injection.backend.MinecraftImpl;
import net.minecraft.client.Minecraft;

public final class WrapperImpl
implements Wrapper {
    private static final IClassProvider classProvider;
    private static final IExtractedFunctions functions;
    public static final WrapperImpl INSTANCE;

    @Override
    public IClassProvider getClassProvider() {
        return classProvider;
    }

    @Override
    public IMinecraft getMinecraft() {
        return new MinecraftImpl(Minecraft.func_71410_x());
    }

    @Override
    public IExtractedFunctions getFunctions() {
        return functions;
    }

    private WrapperImpl() {
    }

    static {
        WrapperImpl wrapperImpl;
        INSTANCE = wrapperImpl = new WrapperImpl();
        classProvider = ClassProviderImpl.INSTANCE;
        functions = ExtractedFunctionsImpl.INSTANCE;
    }
}


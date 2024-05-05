/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.opengl.GL11
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.awt.Color;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="Blink", description="Suspends all movement packets.", category=ModuleCategory.PLAYER)
public final class Blink
extends Module {
    private final LinkedBlockingQueue<IPacket> packets = new LinkedBlockingQueue();
    private IEntityOtherPlayerMP fakePlayer;
    private boolean disableLogger;
    private final LinkedList<double[]> positions = new LinkedList();
    private final BoolValue pulseValue = new BoolValue("Pulse", false);
    private final IntegerValue pulseDelayValue = new IntegerValue("PulseDelay", 1000, 500, 5000);
    private final MSTimer pulseTimer = new MSTimer();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onEnable() {
        IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP == null) {
            return;
        }
        IEntityPlayerSP thePlayer = iEntityPlayerSP;
        if (!((Boolean)this.pulseValue.get()).booleanValue()) {
            IWorldClient iWorldClient = MinecraftInstance.mc.getTheWorld();
            if (iWorldClient == null) {
                Intrinsics.throwNpe();
            }
            IEntityOtherPlayerMP faker = MinecraftInstance.classProvider.createEntityOtherPlayerMP(iWorldClient, thePlayer.getGameProfile());
            faker.setRotationYawHead(thePlayer.getRotationYawHead());
            faker.setRenderYawOffset(thePlayer.getRenderYawOffset());
            faker.copyLocationAndAnglesFrom(thePlayer);
            faker.setRotationYawHead(thePlayer.getRotationYawHead());
            IWorldClient iWorldClient2 = MinecraftInstance.mc.getTheWorld();
            if (iWorldClient2 == null) {
                Intrinsics.throwNpe();
            }
            iWorldClient2.addEntityToWorld(-1337, faker);
            this.fakePlayer = faker;
        }
        LinkedList<double[]> linkedList = this.positions;
        boolean bl = false;
        boolean bl2 = false;
        synchronized (linkedList) {
            boolean bl3 = false;
            this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double)(thePlayer.getEyeHeight() / (float)2), thePlayer.getPosZ()});
            bl2 = this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
        }
        this.pulseTimer.reset();
    }

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.getThePlayer() == null) {
            return;
        }
        this.blink();
        IEntityOtherPlayerMP faker = this.fakePlayer;
        if (faker != null) {
            IWorldClient iWorldClient = MinecraftInstance.mc.getTheWorld();
            if (iWorldClient != null) {
                iWorldClient.removeEntityFromWorld(faker.getEntityId());
            }
            this.fakePlayer = null;
        }
    }

    @EventTarget
    public final void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();
        if (MinecraftInstance.mc.getThePlayer() == null || this.disableLogger) {
            return;
        }
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            event.cancelEvent();
        }
        if (MinecraftInstance.classProvider.isCPacketPlayerPosition(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayerBlockPlacement(packet) || MinecraftInstance.classProvider.isCPacketAnimation(packet) || MinecraftInstance.classProvider.isCPacketEntityAction(packet) || MinecraftInstance.classProvider.isCPacketUseEntity(packet)) {
            event.cancelEvent();
            this.packets.add(packet);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP == null) {
            return;
        }
        IEntityPlayerSP thePlayer = iEntityPlayerSP;
        LinkedList<double[]> linkedList = this.positions;
        boolean bl = false;
        boolean bl2 = false;
        synchronized (linkedList) {
            boolean bl3 = false;
            bl2 = this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
        }
        if (((Boolean)this.pulseValue.get()).booleanValue() && this.pulseTimer.hasTimePassed(((Number)this.pulseDelayValue.get()).intValue())) {
            this.blink();
            this.pulseTimer.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        Breadcrumbs breadcrumbs;
        Breadcrumbs breadcrumbs2 = breadcrumbs = (Breadcrumbs)LiquidBounce.INSTANCE.getModuleManager().getModule(Breadcrumbs.class);
        if (breadcrumbs2 == null) {
            Intrinsics.throwNpe();
        }
        Color color = (Boolean)breadcrumbs2.getColorRainbow().get() != false ? ColorUtils.rainbow() : new Color(((Number)breadcrumbs.getColorRedValue().get()).intValue(), ((Number)breadcrumbs.getColorGreenValue().get()).intValue(), ((Number)breadcrumbs.getColorBlueValue().get()).intValue());
        LinkedList<double[]> linkedList = this.positions;
        boolean bl = false;
        boolean bl2 = false;
        synchronized (linkedList) {
            boolean bl3 = false;
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2929);
            MinecraftInstance.mc.getEntityRenderer().disableLightmap();
            GL11.glBegin((int)3);
            RenderUtils.glColor(color);
            double renderPosX = MinecraftInstance.mc.getRenderManager().getViewerPosX();
            double renderPosY = MinecraftInstance.mc.getRenderManager().getViewerPosY();
            double renderPosZ = MinecraftInstance.mc.getRenderManager().getViewerPosZ();
            for (double[] pos : this.positions) {
                GL11.glVertex3d((double)(pos[0] - renderPosX), (double)(pos[1] - renderPosY), (double)(pos[2] - renderPosZ));
            }
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            GL11.glEnd();
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3553);
            GL11.glPopMatrix();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override
    public String getTag() {
        return String.valueOf(this.packets.size());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void blink() {
        try {
            this.disableLogger = true;
            while (!this.packets.isEmpty()) {
                MinecraftInstance.mc.getNetHandler().getNetworkManager().sendPacket(this.packets.take());
            }
            this.disableLogger = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.disableLogger = false;
        }
        LinkedList<double[]> linkedList = this.positions;
        boolean bl = false;
        boolean bl2 = false;
        synchronized (linkedList) {
            boolean bl3 = false;
            this.positions.clear();
            Unit unit = Unit.INSTANCE;
        }
    }
}


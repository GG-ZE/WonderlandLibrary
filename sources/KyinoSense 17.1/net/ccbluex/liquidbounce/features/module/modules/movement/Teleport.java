/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook
 *  net.minecraft.network.play.client.C0BPacketEntityAction
 *  net.minecraft.network.play.client.C0BPacketEntityAction$Action
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="Teleport", description="Allows you to teleport around.", category=ModuleCategory.FUN)
public class Teleport
extends Module {
    private final BoolValue ignoreNoCollision = new BoolValue("IgnoreNoCollision", true);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Blink", "Flag", "Rewinside", "OldRewinside", "Spoof", "Minesucht", "AAC3.5.0"}, "Blink");
    private final ListValue buttonValue = new ListValue("Button", new String[]{"Left", "Right", "Middle"}, "Middle");
    private final TickTimer flyTimer = new TickTimer();
    private boolean hadGround;
    private double fixedY;
    private final List<Packet<?>> packets = new ArrayList();
    private boolean disableLogger = false;
    private boolean zitter = false;
    private boolean doTeleport = false;
    private boolean freeze = false;
    private final TickTimer freezeTimer = new TickTimer();
    private int delay;
    private BlockPos endPos;
    private MovingObjectPosition objectPosition;

    @Override
    public void onEnable() {
        if (((String)this.modeValue.get()).equalsIgnoreCase("AAC3.5.0")) {
            ClientUtils.displayChatMessage("\u00a7c>>> \u00a7a\u00a7lTeleport \u00a7fAAC 3.5.0 \u00a7c<<<");
            ClientUtils.displayChatMessage("\u00a7cHow to teleport: \u00a7aPress " + (String)this.buttonValue.get() + " mouse button.");
            ClientUtils.displayChatMessage("\u00a7cHow to cancel teleport: \u00a7aDisable teleport module.");
        }
    }

    @Override
    public void onDisable() {
        this.fixedY = 0.0;
        this.delay = 0;
        Teleport.mc.field_71428_T.field_74278_d = 1.0f;
        this.endPos = null;
        this.hadGround = false;
        this.freeze = false;
        this.disableLogger = false;
        this.flyTimer.reset();
        this.packets.clear();
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        int buttonIndex = Arrays.asList(this.buttonValue.getValues()).indexOf(this.buttonValue.get());
        if (((String)this.modeValue.get()).equals("AAC3.5.0")) {
            this.freezeTimer.update();
            if (this.freeze && this.freezeTimer.hasTimePassed(40)) {
                this.freezeTimer.reset();
                this.freeze = false;
                this.setState(false);
            }
            if (!this.flyTimer.hasTimePassed(60)) {
                this.flyTimer.update();
                if (Teleport.mc.field_71439_g.field_70122_E) {
                    Teleport.mc.field_71439_g.func_70664_aZ();
                } else {
                    MovementUtils.forward(this.zitter ? -0.21 : 0.21);
                    this.zitter = !this.zitter;
                }
                this.hadGround = false;
                return;
            }
            if (Teleport.mc.field_71439_g.field_70122_E) {
                this.hadGround = true;
            }
            if (!this.hadGround) {
                return;
            }
            if (Teleport.mc.field_71439_g.field_70122_E) {
                Teleport.mc.field_71439_g.func_70634_a(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u + 0.2, Teleport.mc.field_71439_g.field_70161_v);
            }
            float vanillaSpeed = 2.0f;
            Teleport.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            Teleport.mc.field_71439_g.field_70181_x = 0.0;
            Teleport.mc.field_71439_g.field_70159_w = 0.0;
            Teleport.mc.field_71439_g.field_70179_y = 0.0;
            if (Teleport.mc.field_71474_y.field_74314_A.func_151470_d()) {
                Teleport.mc.field_71439_g.field_70181_x += 2.0;
            }
            if (Teleport.mc.field_71474_y.field_74311_E.func_151470_d()) {
                Teleport.mc.field_71439_g.field_70181_x -= 2.0;
            }
            MovementUtils.strafe(2.0f);
            if (Mouse.isButtonDown((int)buttonIndex) && !this.doTeleport) {
                Teleport.mc.field_71439_g.func_70634_a(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u - 11.0, Teleport.mc.field_71439_g.field_70161_v);
                this.disableLogger = true;
                this.packets.forEach(packet -> mc.func_147114_u().func_147297_a(packet));
                this.freezeTimer.reset();
                this.freeze = true;
            }
            this.doTeleport = Mouse.isButtonDown((int)buttonIndex);
            return;
        }
        if (Teleport.mc.field_71462_r == null && Mouse.isButtonDown((int)buttonIndex) && this.delay <= 0) {
            this.endPos = this.objectPosition.func_178782_a();
            if (BlockUtils.getBlock(this.endPos).func_149688_o() == Material.field_151579_a) {
                this.endPos = null;
                return;
            }
            ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73Position was set to \u00a78" + this.endPos.func_177958_n() + "\u00a73, \u00a78" + ((BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, this.objectPosition.func_178782_a(), BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_176223_P()) == null ? (double)this.endPos.func_177956_o() + BlockUtils.getBlock(this.endPos).func_149669_A() : BlockUtils.getBlock((BlockPos)this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, (BlockPos)this.objectPosition.func_178782_a(), (IBlockState)BlockUtils.getBlock((BlockPos)this.objectPosition.func_178782_a()).func_176223_P()).field_72337_e) + this.fixedY) + "\u00a73, \u00a78" + this.endPos.func_177952_p());
            this.delay = 6;
        }
        if (this.delay > 0) {
            --this.delay;
        }
        if (this.endPos != null) {
            double endX = (double)this.endPos.func_177958_n() + 0.5;
            double endY = (BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, this.objectPosition.func_178782_a(), BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_176223_P()) == null ? (double)this.endPos.func_177956_o() + BlockUtils.getBlock(this.endPos).func_149669_A() : BlockUtils.getBlock((BlockPos)this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, (BlockPos)this.objectPosition.func_178782_a(), (IBlockState)BlockUtils.getBlock((BlockPos)this.objectPosition.func_178782_a()).func_176223_P()).field_72337_e) + this.fixedY;
            double endZ = (double)this.endPos.func_177952_p() + 0.5;
            switch (((String)this.modeValue.get()).toLowerCase()) {
                case "blink": {
                    if (!Teleport.mc.field_71439_g.func_70093_af()) break;
                    mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)Teleport.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
                    PathUtils.findBlinkPath(endX, endY, endZ).forEach(vector3d -> {
                        mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vector3d.x, vector3d.y, vector3d.z, true));
                        Teleport.mc.field_71439_g.func_70107_b(endX, endY, endZ);
                    });
                    mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)Teleport.mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
                    ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73You were teleported to \u00a78" + endX + "\u00a73, \u00a78" + endY + "\u00a73, \u00a78" + endZ);
                    break;
                }
                case "flag": {
                    if (!Teleport.mc.field_71439_g.func_70093_af()) break;
                    mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)Teleport.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(endX, endY, endZ, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u + 5.0, Teleport.mc.field_71439_g.field_70161_v, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(endX, endY, endZ, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t + 0.5, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v + 0.5, true));
                    MovementUtils.forward(0.04);
                    mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)Teleport.mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
                    ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73You were teleported to \u00a78" + endX + "\u00a73, \u00a78" + endY + "\u00a73, \u00a78" + endZ);
                    break;
                }
                case "rewinside": {
                    Teleport.mc.field_71439_g.field_70181_x = 0.1;
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(endX, endY, endZ, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u + 0.6, Teleport.mc.field_71439_g.field_70161_v, true));
                    if ((int)Teleport.mc.field_71439_g.field_70165_t == (int)endX && (int)Teleport.mc.field_71439_g.field_70163_u == (int)endY && (int)Teleport.mc.field_71439_g.field_70161_v == (int)endZ) {
                        ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73You were teleported to \u00a78" + endX + "\u00a73, \u00a78" + endY + "\u00a73, \u00a78" + endZ);
                        this.endPos = null;
                        break;
                    }
                    ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73Teleport try...");
                    break;
                }
                case "oldrewinside": {
                    Teleport.mc.field_71439_g.field_70181_x = 0.1;
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(endX, endY, endZ, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(endX, endY, endZ, true));
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v, true));
                    if ((int)Teleport.mc.field_71439_g.field_70165_t == (int)endX && (int)Teleport.mc.field_71439_g.field_70163_u == (int)endY && (int)Teleport.mc.field_71439_g.field_70161_v == (int)endZ) {
                        ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73You were teleported to \u00a78" + endX + "\u00a73, \u00a78" + endY + "\u00a73, \u00a78" + endZ);
                        this.endPos = null;
                    } else {
                        ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73Teleport try...");
                    }
                    MovementUtils.forward(0.04);
                    break;
                }
                case "minesucht": {
                    if (!Teleport.mc.field_71439_g.func_70093_af()) break;
                    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(endX, endY, endZ, true));
                    ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lTeleport\u00a77] \u00a73You were teleported to \u00a78" + endX + "\u00a73, \u00a78" + endY + "\u00a73, \u00a78" + endZ);
                }
            }
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (((String)this.modeValue.get()).equals("AAC3.5.0")) {
            return;
        }
        Vec3 lookVec = new Vec3(Teleport.mc.field_71439_g.func_70040_Z().field_72450_a * 300.0, Teleport.mc.field_71439_g.func_70040_Z().field_72448_b * 300.0, Teleport.mc.field_71439_g.func_70040_Z().field_72449_c * 300.0);
        Vec3 posVec = new Vec3(Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u + 1.62, Teleport.mc.field_71439_g.field_70161_v);
        this.objectPosition = Teleport.mc.field_71439_g.field_70170_p.func_147447_a(posVec, posVec.func_178787_e(lookVec), false, ((Boolean)this.ignoreNoCollision.get()).booleanValue(), false);
        if (this.objectPosition == null || this.objectPosition.func_178782_a() == null) {
            return;
        }
        BlockPos belowBlockPos = new BlockPos(this.objectPosition.func_178782_a().func_177958_n(), this.objectPosition.func_178782_a().func_177956_o() - 1, this.objectPosition.func_178782_a().func_177952_p());
        this.fixedY = BlockUtils.getBlock(this.objectPosition.func_178782_a()) instanceof BlockFence ? (Teleport.mc.field_71441_e.func_72945_a((Entity)Teleport.mc.field_71439_g, Teleport.mc.field_71439_g.func_174813_aQ().func_72317_d((double)this.objectPosition.func_178782_a().func_177958_n() + 0.5 - Teleport.mc.field_71439_g.field_70165_t, (double)this.objectPosition.func_178782_a().func_177956_o() + 1.5 - Teleport.mc.field_71439_g.field_70163_u, (double)this.objectPosition.func_178782_a().func_177952_p() + 0.5 - Teleport.mc.field_71439_g.field_70161_v)).isEmpty() ? 0.5 : 0.0) : (BlockUtils.getBlock(belowBlockPos) instanceof BlockFence ? (!Teleport.mc.field_71441_e.func_72945_a((Entity)Teleport.mc.field_71439_g, Teleport.mc.field_71439_g.func_174813_aQ().func_72317_d((double)this.objectPosition.func_178782_a().func_177958_n() + 0.5 - Teleport.mc.field_71439_g.field_70165_t, (double)this.objectPosition.func_178782_a().func_177956_o() + 0.5 - Teleport.mc.field_71439_g.field_70163_u, (double)this.objectPosition.func_178782_a().func_177952_p() + 0.5 - Teleport.mc.field_71439_g.field_70161_v)).isEmpty() || BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, this.objectPosition.func_178782_a(), BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_176223_P()) == null ? 0.0 : 0.5 - BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_149669_A()) : (BlockUtils.getBlock(this.objectPosition.func_178782_a()) instanceof BlockSnow ? BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_149669_A() - 0.125 : 0.0));
        int x = this.objectPosition.func_178782_a().func_177958_n();
        double y = (BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, this.objectPosition.func_178782_a(), BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_176223_P()) == null ? (double)this.objectPosition.func_178782_a().func_177956_o() + BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_149669_A() : BlockUtils.getBlock((BlockPos)this.objectPosition.func_178782_a()).func_180640_a((World)Teleport.mc.field_71441_e, (BlockPos)this.objectPosition.func_178782_a(), (IBlockState)BlockUtils.getBlock((BlockPos)this.objectPosition.func_178782_a()).func_176223_P()).field_72337_e) - 1.0 + this.fixedY;
        int z = this.objectPosition.func_178782_a().func_177952_p();
        if (!(BlockUtils.getBlock(this.objectPosition.func_178782_a()) instanceof BlockAir)) {
            RenderManager renderManager = mc.func_175598_ae();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            RenderUtils.glColor(((String)this.modeValue.get()).equalsIgnoreCase("minesucht") && (double)Teleport.mc.field_71439_g.func_180425_c().func_177956_o() != y + 1.0 ? new Color(255, 0, 0, 90) : (!Teleport.mc.field_71441_e.func_72945_a((Entity)Teleport.mc.field_71439_g, Teleport.mc.field_71439_g.func_174813_aQ().func_72317_d((double)x + 0.5 - Teleport.mc.field_71439_g.field_70165_t, y + 1.0 - Teleport.mc.field_71439_g.field_70163_u, (double)z + 0.5 - Teleport.mc.field_71439_g.field_70161_v)).isEmpty() ? new Color(255, 0, 0, 90) : new Color(0, 255, 0, 90)));
            RenderUtils.drawFilledBox(new AxisAlignedBB((double)x - renderManager.field_78725_b, y + 1.0 - renderManager.field_78726_c, (double)z - renderManager.field_78723_d, (double)x - renderManager.field_78725_b + 1.0, y + 1.2 - renderManager.field_78726_c, (double)z - renderManager.field_78723_d + 1.0));
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            RenderUtils.renderNameTag(Math.round(Teleport.mc.field_71439_g.func_70011_f((double)x + 0.5, y + 1.0, (double)z + 0.5)) + "m", (double)x + 0.5, y + 1.7, (double)z + 0.5);
            GlStateManager.func_179117_G();
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (((String)this.modeValue.get()).equalsIgnoreCase("aac3.5.0") && this.freeze) {
            event.zeroXZ();
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (this.disableLogger) {
            return;
        }
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            switch (((String)this.modeValue.get()).toLowerCase()) {
                case "spoof": {
                    if (this.endPos == null) break;
                    packetPlayer.field_149479_a = (double)this.endPos.func_177958_n() + 0.5;
                    packetPlayer.field_149477_b = this.endPos.func_177956_o() + 1;
                    packetPlayer.field_149478_c = (double)this.endPos.func_177952_p() + 0.5;
                    Teleport.mc.field_71439_g.func_70107_b((double)this.endPos.func_177958_n() + 0.5, (double)(this.endPos.func_177956_o() + 1), (double)this.endPos.func_177952_p() + 0.5);
                    break;
                }
                case "aac3.5.0": {
                    if (!this.flyTimer.hasTimePassed(60)) {
                        return;
                    }
                    event.cancelEvent();
                    if (!(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                        return;
                    }
                    this.packets.add(packet);
                }
            }
        }
    }

    @Override
    public String getTag() {
        return (String)this.modeValue.get();
    }
}


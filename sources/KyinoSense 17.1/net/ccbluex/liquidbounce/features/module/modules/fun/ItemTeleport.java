/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3f
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ccbluex.liquidbounce.features.module.modules.fun;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.vecmath.Vector3f;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="ItemTeleport", description="Allows you to pick up items far away.", category=ModuleCategory.FUN)
public class ItemTeleport
extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"New", "Old"}, "New");
    private final BoolValue resetAfterTp = new BoolValue("ResetAfterTP", true);
    private final ListValue buttonValue = new ListValue("Button", new String[]{"Left", "Right", "Middle"}, "Middle");
    private int delay;
    private BlockPos endPos;
    private MovingObjectPosition objectPosition;

    @Override
    public void onDisable() {
        this.delay = 0;
        this.endPos = null;
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (ItemTeleport.mc.field_71462_r == null && Mouse.isButtonDown((int)Arrays.asList(this.buttonValue.getValues()).indexOf(this.buttonValue.get())) && this.delay <= 0) {
            this.endPos = this.objectPosition.func_178782_a();
            if (BlockUtils.getBlock(this.endPos).func_149688_o() == Material.field_151579_a) {
                this.endPos = null;
                return;
            }
            ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lItemTeleport\u00a77] \u00a73Position was set to \u00a78" + this.endPos.func_177958_n() + "\u00a73, \u00a78" + this.endPos.func_177956_o() + "\u00a73, \u00a78" + this.endPos.func_177952_p());
            this.delay = 6;
        }
        if (this.delay > 0) {
            --this.delay;
        }
        if (this.endPos != null && ItemTeleport.mc.field_71439_g.func_70093_af()) {
            if (!ItemTeleport.mc.field_71439_g.field_70122_E) {
                double endX = (double)this.endPos.func_177958_n() + 0.5;
                double endY = (double)this.endPos.func_177956_o() + 1.0;
                double endZ = (double)this.endPos.func_177952_p() + 0.5;
                switch (((String)this.modeValue.get()).toLowerCase()) {
                    case "old": {
                        for (Vector3f vector3f : this.vanillaTeleportPositions(endX, endY, endZ, 4.0)) {
                            mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), false));
                        }
                        break;
                    }
                    case "new": {
                        for (Vector3f vector3f : this.vanillaTeleportPositions(endX, endY, endZ, 5.0)) {
                            mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ItemTeleport.mc.field_71439_g.field_70165_t, ItemTeleport.mc.field_71439_g.field_70163_u, ItemTeleport.mc.field_71439_g.field_70161_v, true));
                            mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition((double)vector3f.x, (double)vector3f.y, (double)vector3f.z, true));
                            mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ItemTeleport.mc.field_71439_g.field_70165_t, ItemTeleport.mc.field_71439_g.field_70163_u, ItemTeleport.mc.field_71439_g.field_70161_v, true));
                            mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ItemTeleport.mc.field_71439_g.field_70165_t, ItemTeleport.mc.field_71439_g.field_70163_u + 4.0, ItemTeleport.mc.field_71439_g.field_70161_v, true));
                            mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition((double)vector3f.x, (double)vector3f.y, (double)vector3f.z, true));
                            MovementUtils.forward(0.04);
                        }
                        break;
                    }
                }
                if (((Boolean)this.resetAfterTp.get()).booleanValue()) {
                    this.endPos = null;
                }
                ClientUtils.displayChatMessage("\u00a77[\u00a78\u00a7lItemTeleport\u00a77] \u00a73Tried to collect items");
            } else {
                ItemTeleport.mc.field_71439_g.func_70664_aZ();
            }
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        this.objectPosition = ItemTeleport.mc.field_71439_g.func_174822_a(1000.0, event.getPartialTicks());
        if (this.objectPosition.func_178782_a() == null) {
            return;
        }
        int x = this.objectPosition.func_178782_a().func_177958_n();
        int y = this.objectPosition.func_178782_a().func_177956_o();
        int z = this.objectPosition.func_178782_a().func_177952_p();
        if (BlockUtils.getBlock(this.objectPosition.func_178782_a()).func_149688_o() != Material.field_151579_a) {
            RenderManager renderManager = mc.func_175598_ae();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            RenderUtils.glColor(BlockUtils.getBlock(this.objectPosition.func_178782_a().func_177984_a()).func_149688_o() != Material.field_151579_a ? new Color(255, 0, 0, 90) : new Color(0, 255, 0, 90));
            RenderUtils.drawFilledBox(new AxisAlignedBB((double)x - renderManager.field_78725_b, (double)(y + 1) - renderManager.field_78726_c, (double)z - renderManager.field_78723_d, (double)x - renderManager.field_78725_b + 1.0, (double)y + 1.2 - renderManager.field_78726_c, (double)z - renderManager.field_78723_d + 1.0));
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            RenderUtils.renderNameTag(Math.round(ItemTeleport.mc.field_71439_g.func_70011_f((double)x, (double)y, (double)z)) + "m", (double)x + 0.5, (double)y + 1.7, (double)z + 0.5);
            GlStateManager.func_179117_G();
        }
    }

    private List<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
        double d;
        ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
        double posX = tpX - ItemTeleport.mc.field_71439_g.field_70165_t;
        double posZ = tpZ - ItemTeleport.mc.field_71439_g.field_70161_v;
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI - 90.0);
        double tmpY = ItemTeleport.mc.field_71439_g.field_70163_u;
        double steps = 1.0;
        for (d = speed; d < this.getDistance(ItemTeleport.mc.field_71439_g.field_70165_t, ItemTeleport.mc.field_71439_g.field_70163_u, ItemTeleport.mc.field_71439_g.field_70161_v, tpX, tpY, tpZ); d += speed) {
            steps += 1.0;
        }
        for (d = speed; d < this.getDistance(ItemTeleport.mc.field_71439_g.field_70165_t, ItemTeleport.mc.field_71439_g.field_70163_u, ItemTeleport.mc.field_71439_g.field_70161_v, tpX, tpY, tpZ); d += speed) {
            double tmpX = ItemTeleport.mc.field_71439_g.field_70165_t - Math.sin(Math.toRadians(yaw)) * d;
            double tmpZ = ItemTeleport.mc.field_71439_g.field_70161_v + Math.cos(Math.toRadians(yaw)) * d;
            positions.add(new Vector3f((float)tmpX, (float)(tmpY -= (ItemTeleport.mc.field_71439_g.field_70163_u - tpY) / steps), (float)tmpZ));
        }
        positions.add(new Vector3f((float)tpX, (float)tpY, (float)tpZ));
        return positions;
    }

    private double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return MathHelper.func_76133_a((double)(d0 * d0 + d1 * d1 + d2 * d2));
    }
}


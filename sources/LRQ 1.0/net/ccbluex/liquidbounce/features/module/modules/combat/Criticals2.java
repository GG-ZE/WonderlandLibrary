/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.TypeCastException
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name="Criticals2", description="Automatically deals critical hits.", category=ModuleCategory.COMBAT)
public final class Criticals2
extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"HuaYuTing", "sb", "NewPacket", "Duel", "HytTest", "HytTest2", "Hyt-Tg-Packet-New", "AAC4Hyt", "Lite", "HytTest", "HytVulcan", "Vulcan", "vulcanfake", "Spartan", "StarPacket", "Packet", "NcpPacket", "NoGround", "Hop", "TPHop", "Jump", "LowJump", "Visual"}, "Packet");
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 500);
    private final BoolValue lookValue = new BoolValue("SendC06", false);
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private int n;
    private int attacks;
    private final MSTimer msTimer = new MSTimer();

    public final ListValue getModeValue() {
        return this.modeValue;
    }

    public final IntegerValue getDelayValue() {
        return this.delayValue;
    }

    public final int getN() {
        return this.n;
    }

    public final void setN(int n) {
        this.n = n;
    }

    public final int getAttacks() {
        return this.attacks;
    }

    public final void setAttacks(int n) {
        this.attacks = n;
    }

    public final MSTimer getMsTimer() {
        return this.msTimer;
    }

    private final void sendCriticalPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
        IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP == null) {
            Intrinsics.throwNpe();
        }
        double x = iEntityPlayerSP.getPosX() + xOffset;
        IEntityPlayerSP iEntityPlayerSP2 = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP2 == null) {
            Intrinsics.throwNpe();
        }
        double y = iEntityPlayerSP2.getPosY() + yOffset;
        IEntityPlayerSP iEntityPlayerSP3 = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP3 == null) {
            Intrinsics.throwNpe();
        }
        double z = iEntityPlayerSP3.getPosZ() + zOffset;
        if (((Boolean)this.lookValue.get()).booleanValue()) {
            IINetHandlerPlayClient iINetHandlerPlayClient = MinecraftInstance.mc.getNetHandler();
            IEntityPlayerSP iEntityPlayerSP4 = MinecraftInstance.mc.getThePlayer();
            if (iEntityPlayerSP4 == null) {
                Intrinsics.throwNpe();
            }
            float f = iEntityPlayerSP4.getRotationYaw();
            IEntityPlayerSP iEntityPlayerSP5 = MinecraftInstance.mc.getThePlayer();
            if (iEntityPlayerSP5 == null) {
                Intrinsics.throwNpe();
            }
            iINetHandlerPlayClient.addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosLook(x, y, z, f, iEntityPlayerSP5.getRotationPitch(), ground));
        } else {
            MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, ground));
        }
    }

    static /* synthetic */ void sendCriticalPacket$default(Criticals2 criticals2, double d, double d2, double d3, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            d = 0.0;
        }
        if ((n & 2) != 0) {
            d2 = 0.0;
        }
        if ((n & 4) != 0) {
            d3 = 0.0;
        }
        criticals2.sendCriticalPacket(d, d2, d3, bl);
    }

    @Override
    public void onEnable() {
        if (StringsKt.equals((String)((String)this.modeValue.get()), (String)"NoGround", (boolean)true)) {
            IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
            if (iEntityPlayerSP == null) {
                Intrinsics.throwNpe();
            }
            iEntityPlayerSP.jump();
        }
    }

    @EventTarget
    public final void onAttack(AttackEvent event) {
        if (MinecraftInstance.classProvider.isEntityLivingBase(event.getTargetEntity())) {
            IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
            if (iEntityPlayerSP == null) {
                return;
            }
            IEntityPlayerSP thePlayer = iEntityPlayerSP;
            IEntity iEntity = event.getTargetEntity();
            if (iEntity == null) {
                Intrinsics.throwNpe();
            }
            IEntityLivingBase entity = iEntity.asEntityLivingBase();
            if (!thePlayer.getOnGround() || thePlayer.isOnLadder() || thePlayer.isInWeb() || thePlayer.isInWater() || thePlayer.isInLava() || thePlayer.getRidingEntity() != null || entity.getHurtTime() > ((Number)this.hurtTimeValue.get()).intValue() || LiquidBounce.INSTANCE.getModuleManager().get(Fly.class).getState() || !this.msTimer.hasTimePassed(((Number)this.delayValue.get()).intValue())) {
                return;
            }
            double x = thePlayer.getPosX();
            double y = thePlayer.getPosY();
            double z = thePlayer.getPosZ();
            String string = (String)this.modeValue.get();
            int n = 0;
            String string2 = string;
            if (string2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            switch (string2.toLowerCase()) {
                case "huayuting": {
                    n = this.attacks;
                    this.attacks = n + 1;
                    double[] normalOffset = new double[]{0.12, 0.023, 0.3};
                    if (this.attacks > 2) {
                        thePlayer.setSprinting(false);
                        thePlayer.setServerSprintState(false);
                        for (double offSet : normalOffset) {
                            Criticals2.sendCriticalPacket$default(this, 0.0, offSet, 0.0, false, 5, null);
                            this.attacks = 0;
                        }
                        break;
                    }
                    if (this.attacks != 0) break;
                    thePlayer.setSprinting(true);
                    thePlayer.setServerSprintState(true);
                    break;
                }
                case "duel": {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.6251314, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0181314, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0011314, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.001314, z, false));
                    thePlayer.onCriticalHit(entity);
                    break;
                }
                case "sb": {
                    this.n = this.attacks;
                    int normalOffset = this.attacks = this.n + 1;
                    this.attacks = normalOffset + 1;
                    if (this.attacks < 5) break;
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0114514, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.001919, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0, z, false));
                    this.attacks = 0;
                    break;
                }
                case "vulcanfake": {
                    double motionX = 0.0;
                    double motionZ = 0.0;
                    if (MovementUtils.isMoving()) {
                        IEntityPlayerSP iEntityPlayerSP2 = MinecraftInstance.mc.getThePlayer();
                        if (iEntityPlayerSP2 == null) {
                            Intrinsics.throwNpe();
                        }
                        motionX = iEntityPlayerSP2.getMotionX();
                        IEntityPlayerSP iEntityPlayerSP3 = MinecraftInstance.mc.getThePlayer();
                        if (iEntityPlayerSP3 == null) {
                            Intrinsics.throwNpe();
                        }
                        motionZ = iEntityPlayerSP3.getMotionZ();
                    } else {
                        motionX = 0.0;
                        motionZ = 0.0;
                    }
                    this.sendCriticalPacket(motionX / (double)3, 0.20000004768372, motionZ / (double)3, false);
                    this.sendCriticalPacket(motionX / 1.5, 0.12160004615784, motionZ / 1.5, false);
                    break;
                }
                case "hyttest": {
                    IINetHandlerPlayClient iINetHandlerPlayClient = MinecraftInstance.mc.getNetHandler();
                    IEntityPlayerSP iEntityPlayerSP4 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP4 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d = iEntityPlayerSP4.getPosX();
                    IEntityPlayerSP iEntityPlayerSP5 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP5 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d2 = iEntityPlayerSP5.getPosY() + 1.100134977413E-5;
                    IEntityPlayerSP iEntityPlayerSP6 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP6 == null) {
                        Intrinsics.throwNpe();
                    }
                    iINetHandlerPlayClient.addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(d, d2, iEntityPlayerSP6.getPosZ(), false));
                    IINetHandlerPlayClient iINetHandlerPlayClient2 = MinecraftInstance.mc.getNetHandler();
                    IEntityPlayerSP iEntityPlayerSP7 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP7 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d3 = iEntityPlayerSP7.getPosX();
                    IEntityPlayerSP iEntityPlayerSP8 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP8 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d4 = iEntityPlayerSP8.getPosY() + 1.3487744E-10;
                    IEntityPlayerSP iEntityPlayerSP9 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP9 == null) {
                        Intrinsics.throwNpe();
                    }
                    iINetHandlerPlayClient2.addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(d3, d4, iEntityPlayerSP9.getPosZ(), false));
                    IINetHandlerPlayClient iINetHandlerPlayClient3 = MinecraftInstance.mc.getNetHandler();
                    IEntityPlayerSP iEntityPlayerSP10 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP10 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d5 = iEntityPlayerSP10.getPosX();
                    IEntityPlayerSP iEntityPlayerSP11 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP11 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d6 = iEntityPlayerSP11.getPosY() + 5.71003114589E-6;
                    IEntityPlayerSP iEntityPlayerSP12 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP12 == null) {
                        Intrinsics.throwNpe();
                    }
                    iINetHandlerPlayClient3.addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(d5, d6, iEntityPlayerSP12.getPosZ(), false));
                    IINetHandlerPlayClient iINetHandlerPlayClient4 = MinecraftInstance.mc.getNetHandler();
                    IEntityPlayerSP iEntityPlayerSP13 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP13 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d7 = iEntityPlayerSP13.getPosX();
                    IEntityPlayerSP iEntityPlayerSP14 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP14 == null) {
                        Intrinsics.throwNpe();
                    }
                    double d8 = iEntityPlayerSP14.getPosY() + 1.578887744E-8;
                    IEntityPlayerSP iEntityPlayerSP15 = MinecraftInstance.mc.getThePlayer();
                    if (iEntityPlayerSP15 == null) {
                        Intrinsics.throwNpe();
                    }
                    iINetHandlerPlayClient4.addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(d7, d8, iEntityPlayerSP15.getPosZ(), false));
                    break;
                }
                case "hytvulcan": {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 5.11322554E-4, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.1119999543618E-4, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 6.221E-5, z, false));
                    break;
                }
                case "vulcan": {
                    int motionX = this.attacks;
                    this.attacks = motionX + 1;
                    if (this.attacks <= 6) break;
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.2, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.1216, z, false));
                    this.attacks = 0;
                    break;
                }
                case "aac4hyt": {
                    int motionX = this.attacks;
                    this.attacks = motionX + 1;
                    if (this.attacks <= 5) break;
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.0114514, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.0019, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 1.0E-6, 0.0, false, 5, null);
                    this.attacks = 0;
                    break;
                }
                case "hyttest": {
                    this.n = this.attacks;
                    this.attacks = this.n + 1;
                    if (this.attacks < 6) break;
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.01011, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.0E-10, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.10114, z, false));
                    thePlayer.onCriticalHit(entity);
                    this.attacks = 0;
                    break;
                }
                case "hyttest2": {
                    this.n = this.attacks;
                    this.attacks = this.n + 1;
                    if (this.attacks < 12) break;
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.01011, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.0E-10, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.05014, z, false));
                    thePlayer.onCriticalHit(entity);
                    this.attacks = 0;
                    break;
                }
                case "hyt-tg-packet-new": {
                    this.n = this.attacks;
                    this.attacks = this.n + 1;
                    if (this.attacks <= 6) break;
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.01, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.0E-10, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.114514, z, false));
                    this.attacks = 0;
                    break;
                }
                case "spartan": {
                    double motionX = 0.0;
                    double motionZ = 0.0;
                    if (MovementUtils.isMoving()) {
                        IEntityPlayerSP iEntityPlayerSP16 = MinecraftInstance.mc.getThePlayer();
                        if (iEntityPlayerSP16 == null) {
                            Intrinsics.throwNpe();
                        }
                        motionX = iEntityPlayerSP16.getMotionX();
                        IEntityPlayerSP iEntityPlayerSP17 = MinecraftInstance.mc.getThePlayer();
                        if (iEntityPlayerSP17 == null) {
                            Intrinsics.throwNpe();
                        }
                        motionZ = iEntityPlayerSP17.getMotionZ();
                    } else {
                        motionX = 0.0;
                        motionZ = 0.0;
                    }
                    this.sendCriticalPacket(motionX / (double)3, 0.20000004768372, motionZ / (double)3, false);
                    this.sendCriticalPacket(motionX / 1.5, 0.12160004615784, motionZ / 1.5, false);
                    break;
                }
                case "starpacket": {
                    Criticals2.sendCriticalPacket$default(this, 0.0, 6.6666E-6, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 7.8E-7, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 1.14514E-6, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.0, 0.0, false, 7, null);
                    break;
                }
                case "newpacket": {
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.05250000001304, 0.0, true, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.00150000001304, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.01400000001304, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.00150000001304, 0.0, false, 5, null);
                    break;
                }
                case "lite": {
                    Criticals2.sendCriticalPacket$default(this, 0.0, 0.015626, 0.0, false, 5, null);
                    Criticals2.sendCriticalPacket$default(this, 0.0, 3.43E-9, 0.0, false, 5, null);
                    break;
                }
                case "packet": {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0625, z, true));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.1E-5, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                    thePlayer.onCriticalHit(entity);
                    break;
                }
                case "ncppacket": {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.11, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.1100013579, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.3579E-6, z, false));
                    thePlayer.onCriticalHit(entity);
                    break;
                }
                case "hop": {
                    thePlayer.setMotionY(0.1);
                    thePlayer.setFallDistance(0.1f);
                    thePlayer.setOnGround(false);
                    break;
                }
                case "tphop": {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.02, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.01, z, false));
                    thePlayer.setPosition(x, y + 0.01, z);
                    break;
                }
                case "jump": {
                    thePlayer.setMotionY(0.42);
                    break;
                }
                case "lowjump": {
                    thePlayer.setMotionY(0.3425);
                    break;
                }
                case "visual": {
                    thePlayer.onCriticalHit(entity);
                }
            }
            this.msTimer.reset();
        }
    }

    @EventTarget
    public final void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) && StringsKt.equals((String)((String)this.modeValue.get()), (String)"NoGround", (boolean)true)) {
            packet.asCPacketPlayer().setOnGround(false);
        }
    }

    @Override
    public String getTag() {
        return (String)this.modeValue.get();
    }
}


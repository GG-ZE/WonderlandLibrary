/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.DefaultConstructorMarker
 */
package net.ccbluex.liquidbounce.utils.block;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;

public final class PlaceInfo {
    private final WBlockPos blockPos;
    private final IEnumFacing enumFacing;
    private WVec3 vec3;
    public static final Companion Companion = new Companion(null);

    public final WBlockPos getBlockPos() {
        return this.blockPos;
    }

    public final IEnumFacing getEnumFacing() {
        return this.enumFacing;
    }

    public final WVec3 getVec3() {
        return this.vec3;
    }

    public final void setVec3(WVec3 wVec3) {
        this.vec3 = wVec3;
    }

    public PlaceInfo(WBlockPos blockPos, IEnumFacing enumFacing, WVec3 vec3) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
        this.vec3 = vec3;
    }

    public /* synthetic */ PlaceInfo(WBlockPos wBlockPos, IEnumFacing iEnumFacing, WVec3 wVec3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            wVec3 = new WVec3((double)wBlockPos.getX() + 0.5, (double)wBlockPos.getY() + 0.5, (double)wBlockPos.getZ() + 0.5);
        }
        this(wBlockPos, iEnumFacing, wVec3);
    }

    @JvmStatic
    public static final PlaceInfo get(WBlockPos blockPos) {
        return Companion.get(blockPos);
    }

    public static final class Companion {
        @JvmStatic
        public final PlaceInfo get(WBlockPos blockPos) {
            if (BlockUtils.canBeClicked(blockPos.add(0, -1, 0))) {
                return new PlaceInfo(blockPos.add(0, -1, 0), WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.UP), null, 4, null);
            }
            if (BlockUtils.canBeClicked(blockPos.add(0, 0, 1))) {
                return new PlaceInfo(blockPos.add(0, 0, 1), WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.NORTH), null, 4, null);
            }
            if (BlockUtils.canBeClicked(blockPos.add(-1, 0, 0))) {
                return new PlaceInfo(blockPos.add(-1, 0, 0), WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.EAST), null, 4, null);
            }
            if (BlockUtils.canBeClicked(blockPos.add(0, 0, -1))) {
                return new PlaceInfo(blockPos.add(0, 0, -1), WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.SOUTH), null, 4, null);
            }
            return BlockUtils.canBeClicked(blockPos.add(1, 0, 0)) ? new PlaceInfo(blockPos.add(1, 0, 0), WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.WEST), null, 4, null) : null;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


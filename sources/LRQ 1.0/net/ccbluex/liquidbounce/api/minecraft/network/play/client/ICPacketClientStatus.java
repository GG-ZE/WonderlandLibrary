/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package net.ccbluex.liquidbounce.api.minecraft.network.play.client;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0002\u00a8\u0006\u0003"}, d2={"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "WEnumState", "LiquidSense"})
public interface ICPacketClientStatus
extends IPacket {

    public static final class WEnumState
    extends Enum<WEnumState> {
        public static final /* enum */ WEnumState PERFORM_RESPAWN;
        public static final /* enum */ WEnumState REQUEST_STATS;
        public static final /* enum */ WEnumState OPEN_INVENTORY_ACHIEVEMENT;
        private static final /* synthetic */ WEnumState[] $VALUES;

        static {
            WEnumState[] wEnumStateArray = new WEnumState[3];
            WEnumState[] wEnumStateArray2 = wEnumStateArray;
            wEnumStateArray[0] = PERFORM_RESPAWN = new WEnumState();
            wEnumStateArray[1] = REQUEST_STATS = new WEnumState();
            wEnumStateArray[2] = OPEN_INVENTORY_ACHIEVEMENT = new WEnumState();
            $VALUES = wEnumStateArray;
        }

        public static WEnumState[] values() {
            return (WEnumState[])$VALUES.clone();
        }

        public static WEnumState valueOf(String string) {
            return Enum.valueOf(WEnumState.class, string);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventState;

public final class Sneak$WhenMappings {
    public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];

    static {
        Sneak$WhenMappings.$EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
        Sneak$WhenMappings.$EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
    }
}


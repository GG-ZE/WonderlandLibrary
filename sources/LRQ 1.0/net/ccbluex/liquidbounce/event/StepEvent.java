/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import net.ccbluex.liquidbounce.event.Event;

public final class StepEvent
extends Event {
    private float stepHeight;

    public final float getStepHeight() {
        return this.stepHeight;
    }

    public final void setStepHeight(float f) {
        this.stepHeight = f;
    }

    public StepEvent(float stepHeight) {
        this.stepHeight = stepHeight;
    }
}


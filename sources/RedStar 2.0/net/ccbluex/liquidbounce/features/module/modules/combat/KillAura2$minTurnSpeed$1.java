package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura2;
import net.ccbluex.liquidbounce.value.FloatValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\n\u0000\n\n\u0000\n\n\u0000\n\n\b*\u0000\b\n\u000020J02020H¨"}, d2={"net/ccbluex/liquidbounce/features/module/modules/combat/KillAura2$minTurnSpeed$1", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onChanged", "", "oldValue", "", "newValue", "Pride"})
public static final class KillAura2$minTurnSpeed$1
extends FloatValue {
    final KillAura2 this$0;

    @Override
    protected void onChanged(float oldValue, float newValue) {
        float v = ((Number)this.this$0.maxTurnSpeed.get()).floatValue();
        if (v < newValue) {
            this.set(Float.valueOf(v));
        }
    }

    KillAura2$minTurnSpeed$1(KillAura2 $outer, String $super_call_param$1, float $super_call_param$2, float $super_call_param$3, float $super_call_param$4) {
        this.this$0 = $outer;
        super($super_call_param$1, $super_call_param$2, $super_call_param$3, $super_call_param$4);
    }
}

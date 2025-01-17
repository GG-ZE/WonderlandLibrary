/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package net.dev.important.event;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.dev.important.event.EventTarget;
import net.dev.important.event.Listenable;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2={"Lnet/dev/important/event/EventHook;", "", "eventClass", "Lnet/dev/important/event/Listenable;", "method", "Ljava/lang/reflect/Method;", "eventTarget", "Lnet/dev/important/event/EventTarget;", "(Lnet/dev/important/event/Listenable;Ljava/lang/reflect/Method;Lnet/dev/important/event/EventTarget;)V", "priority", "", "(Lnet/dev/important/event/Listenable;Ljava/lang/reflect/Method;ILnet/dev/important/event/EventTarget;)V", "getEventClass", "()Lnet/dev/important/event/Listenable;", "isIgnoreCondition", "", "()Z", "getMethod", "()Ljava/lang/reflect/Method;", "getPriority", "()I", "LiquidBounce"})
public final class EventHook {
    @NotNull
    private final Listenable eventClass;
    @NotNull
    private final Method method;
    private final int priority;
    private final boolean isIgnoreCondition;

    public EventHook(@NotNull Listenable eventClass, @NotNull Method method, int priority, @NotNull EventTarget eventTarget) {
        Intrinsics.checkNotNullParameter(eventClass, "eventClass");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(eventTarget, "eventTarget");
        this.eventClass = eventClass;
        this.method = method;
        this.priority = priority;
        this.isIgnoreCondition = eventTarget.ignoreCondition();
    }

    @NotNull
    public final Listenable getEventClass() {
        return this.eventClass;
    }

    @NotNull
    public final Method getMethod() {
        return this.method;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final boolean isIgnoreCondition() {
        return this.isIgnoreCondition;
    }

    public EventHook(@NotNull Listenable eventClass, @NotNull Method method, @NotNull EventTarget eventTarget) {
        Intrinsics.checkNotNullParameter(eventClass, "eventClass");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(eventTarget, "eventTarget");
        this(eventClass, method, 0, eventTarget);
    }
}


package io.github.raze.events.system;

import io.github.raze.utilities.collection.arrays.ArrayUtil;
import io.github.raze.Raze;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Event {

    public boolean cancelled;
    public State state;

    public enum State {
        PRE, POST
    }

    public static class Data {

        public Object source;
        public Method target;

        public byte priority;

        public Data(Object source, Method target, byte priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }

    }

    public static class Priority {

        public static byte FIRST = 0, SECOND = 1, THIRD = 2, FOURTH = 3, FIFTH = 4;

        public static byte[] VALUE_ARRAY = new byte[] {0, 1, 2, 3, 4};

    }

    public Event() {
        this.cancelled = false;
        this.state = State.PRE;
    }

    public void call() {
        setCancelled(false);
        Raze.INSTANCE.managerRegistry.eventManager.publish(this);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

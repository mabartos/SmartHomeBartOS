package org.mabartos.protocols.mqtt.data.capability;

public enum State {
    ON,
    OFF;

    State() {
    }

    public static State changeState(State state) {
        if (state.equals(ON))
            return OFF;
        return ON;
    }
}

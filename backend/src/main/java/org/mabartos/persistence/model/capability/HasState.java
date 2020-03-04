package org.mabartos.persistence.model.capability;

import org.mabartos.protocols.mqtt.data.capability.State;

public interface HasState {

    State getState();

    void setState(State state);

    void changeState();
}

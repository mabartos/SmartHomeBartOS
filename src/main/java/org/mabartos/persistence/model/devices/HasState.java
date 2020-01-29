package org.mabartos.persistence.model.devices;

public interface HasState {

    boolean getState();

    void setState(boolean state);

    void changeState();
}

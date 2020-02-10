package org.mabartos.persistence.model.capability;

public interface HasState {

    boolean getState();

    void setState(boolean state);

    void changeState();
}

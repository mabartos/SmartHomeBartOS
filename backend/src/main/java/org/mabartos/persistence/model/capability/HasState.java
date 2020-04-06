package org.mabartos.persistence.model.capability;

public interface HasState {

    boolean isTurnedOn();

    void setState(boolean state);

    void changeState();
}

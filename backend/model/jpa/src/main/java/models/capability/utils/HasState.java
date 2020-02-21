package models.capability.utils;

public interface HasState {

    boolean getState();

    void setState(boolean state);

    void changeState();
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability;

public interface HasState {

    boolean isTurnedOn();

    void setState(boolean state);

    void changeState();
}

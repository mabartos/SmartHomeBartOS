/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability;

import org.mabartos.api.common.values.HasValueType;
import org.mabartos.api.common.values.ValuesType;

public interface HasState extends HasValueType {

    boolean isTurnedOn();

    void setState(boolean state);

    void changeState();

    default ValuesType getValueType() {
        return ValuesType.ON_OFF;
    }
}

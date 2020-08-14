/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.humidity;

import org.mabartos.api.model.capability.InputCapModel;
import org.mabartos.api.service.capability.HasValueAndUnits;

public interface HumidityCapModel extends InputCapModel, HasValueAndUnits<Byte> {

    default Byte getMinValue() {
        return 0;
    }

    default Byte getMaxValue() {
        return 100;
    }

    default boolean checkValidValue(Byte value) {
        return (value >= getMinValue() && value <= getMaxValue());
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.temperature;

import org.mabartos.api.model.capability.InputCapModel;
import org.mabartos.api.service.capability.HasValueAndUnits;

public interface TemperatureCapModel extends InputCapModel, HasValueAndUnits<Double> {

    Double MAX_SCOPE = 1000.0;

    default Double getMinValue() {
        return -MAX_SCOPE;
    }

    default Double getMaxValue() {
        return MAX_SCOPE;
    }

    default boolean checkValidValue(Double value) {
        return (value >= getMinValue() && value <= getMaxValue());
    }
}

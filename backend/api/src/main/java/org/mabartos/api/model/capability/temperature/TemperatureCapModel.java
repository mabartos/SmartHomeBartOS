/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.temperature;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.service.capability.HasValueAndUnits;

public interface TemperatureCapModel extends CapabilityModel, HasValueAndUnits<Double> {

    Double MAX_SCOPE = 1000.0;
    
    default Double getMinValue() {
        return -MAX_SCOPE;
    }

    default Double getMaxValue() {
        return MAX_SCOPE;
    }
}

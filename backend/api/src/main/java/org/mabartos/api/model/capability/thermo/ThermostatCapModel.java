/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.thermo;

import org.mabartos.api.common.values.ValuesType;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.service.capability.HasState;
import org.mabartos.api.service.capability.HasValueAndUnits;

public interface ThermostatCapModel extends CapabilityModel, HasState, HasValueAndUnits<Double> {

    boolean isCoolingActive();

    void setCooling();

    void setHeating();

    Double getDestTemperature();

    void setDestTemperature(Double temperature);

    default ValuesType getValueType() {
        return ValuesType.BOTH;
    }
}

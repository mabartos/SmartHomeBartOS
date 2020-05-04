package org.mabartos.api.model.capability.heater;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasState;

public interface HeaterCapModel extends CapabilityModel, HasState {
    Double getDestTemperature();

    void setDestTemperature(Double destTemperature);
}

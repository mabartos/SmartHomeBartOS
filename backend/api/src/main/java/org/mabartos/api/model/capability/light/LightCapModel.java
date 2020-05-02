package org.mabartos.api.model.capability.light;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasState;

public interface LightCapModel extends CapabilityModel, HasState {

    Byte getIntensity();

    void setIntensity(Byte intensity);

    Byte getMinIntensity();

    void setMinIntensity(Byte minIntensity);
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.light;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.service.capability.HasNumberValueScope;
import org.mabartos.api.service.capability.HasState;

public interface LightCapModel extends CapabilityModel, HasState, HasNumberValueScope<Byte> {

    Byte getIntensity();

    void setIntensity(Byte intensity);

    Byte getMinIntensity();

    void setMinIntensity(Byte minIntensity);

    default Byte getMinValue() {
        return 0;
    }

    default Byte getMaxValue() {
        return 100;
    }
}

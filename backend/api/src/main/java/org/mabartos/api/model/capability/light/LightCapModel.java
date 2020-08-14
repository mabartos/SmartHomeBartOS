/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.light;

import org.mabartos.api.common.values.ValuesType;
import org.mabartos.api.model.capability.OutputCapModel;
import org.mabartos.api.service.capability.HasNumberValueScope;
import org.mabartos.api.service.capability.HasState;

public interface LightCapModel extends OutputCapModel, HasState, HasNumberValueScope<Byte> {

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

    default boolean checkValidValue(Byte value) {
        return (value >= getMinValue() && value <= getMaxValue());
    }

    @Override
    default ValuesType getValueType() {
        return ValuesType.NUMBER;
    }
}

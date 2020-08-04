/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability;

import org.mabartos.api.common.values.HasValueType;
import org.mabartos.api.common.values.ValuesType;

public interface HasNumberValue<T extends Number> extends HasValueType {
    T getValue();

    void setValue(T value);

    default ValuesType getValueType() {
        return ValuesType.NUMBER;
    }
}

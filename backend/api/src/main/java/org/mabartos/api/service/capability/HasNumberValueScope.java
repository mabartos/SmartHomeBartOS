/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability;

public interface HasNumberValueScope<T extends Number> extends HasNumberValue<T> {
    T getMinValue();

    T getMaxValue();

    boolean checkValidValue(T value);
}

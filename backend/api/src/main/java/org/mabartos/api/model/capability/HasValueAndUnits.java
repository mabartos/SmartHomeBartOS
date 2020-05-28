/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability;

public interface HasValueAndUnits<T> extends HasValue<T> {

    String getUnits();

    void setUnits(String units);
}

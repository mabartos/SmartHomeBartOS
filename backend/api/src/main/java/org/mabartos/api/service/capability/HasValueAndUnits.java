/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability;

public interface HasValueAndUnits<T extends Number> extends HasNumberValueScope<T> {

    String getUnits();

    void setUnits(String units);
}

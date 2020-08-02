/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.temperature;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasValue;
import org.mabartos.api.model.capability.HasValueAndUnits;

public interface TemperatureCapModel extends CapabilityModel, HasValueAndUnits<Double> {
}

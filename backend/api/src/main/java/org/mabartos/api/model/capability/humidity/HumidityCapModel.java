/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.humidity;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasValueAndUnits;

public interface HumidityCapModel extends CapabilityModel, HasValueAndUnits<Byte> {
}

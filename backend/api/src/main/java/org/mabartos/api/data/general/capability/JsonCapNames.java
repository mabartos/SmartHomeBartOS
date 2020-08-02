/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability;

import org.mabartos.api.data.general.JsonPropertyNames;

public interface JsonCapNames extends JsonPropertyNames {
    String ACTUAL_VALUE = "actual";
    String STATE = "isTurnedOn";

    String INTENSITY = "intensity";
    String MIN_INTENSITY = "minIntensity";

}

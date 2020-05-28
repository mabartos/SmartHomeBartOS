/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mabartos.api.data.mqtt.ConvertableToModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CapabilityData implements ConvertableToModel {

    public CapabilityData() {
    }
}

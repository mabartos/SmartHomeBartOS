/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.pir;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithState;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.pir.PIRCapModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PIRData extends CapabilityDataWithState {

    public PIRData(@JsonProperty(JsonCapNames.STATE) boolean isTurnedOn) {
        super(isTurnedOn);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof PIRCapModel) {
            super.editModel(model);
            return model;
        }
        return null;
    }

    public static PIRData fromJson(String json) {
        return SerializeUtils.fromJson(json, PIRData.class);
    }
}

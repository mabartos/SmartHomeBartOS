/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.temperature;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapDataWithNumberValue;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.temperature.TemperatureCapModel;
import org.mabartos.api.service.capability.CapabilityType;

public class TemperatureData extends CapDataWithNumberValue<Double> {

    @JsonCreator
    public TemperatureData(@JsonProperty(JsonCapNames.ACTUAL_VALUE) Double actualTemperature) {
        super(actualTemperature);
    }

    public static TemperatureData fromJson(String json) {
        return SerializeUtils.fromJson(json, TemperatureData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof TemperatureCapModel) {
            super.editModel(model);
            TemperatureCapModel temp = (TemperatureCapModel) model;
            temp.setType(CapabilityType.TEMPERATURE);
            return temp;
        }
        return null;
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.temperature;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithValue;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.temperature.TemperatureCapModel;

public class TemperatureData extends CapabilityDataWithValue<Double> {

    @JsonCreator
    public TemperatureData(@JsonProperty("actual") Double actualTemperature) {
        super(actualTemperature);
    }

    @Override
    public Double getValue() {
        return actual;
    }

    @Override
    public void setValue(Double value) {
        this.actual = value;
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

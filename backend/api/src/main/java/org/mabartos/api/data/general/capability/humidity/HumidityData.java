/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.humidity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithValue;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.humidity.HumidityCapModel;

public class HumidityData extends CapabilityDataWithValue<Byte> {

    public HumidityData(@JsonProperty(JsonCapNames.ACTUAL_VALUE) Byte actual) {
        super(actual);
    }

    public static HumidityData fromJson(String json) {
        return SerializeUtils.fromJson(json, HumidityData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof HumidityCapModel) {
            super.editModel(model);
            HumidityCapModel hum = (HumidityCapModel) model;
            hum.setType(CapabilityType.HUMIDITY);
            return hum;
        }
        return null;
    }
}

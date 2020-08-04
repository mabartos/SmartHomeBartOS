/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.light;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithState;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.light.LightCapModel;
import org.mabartos.api.service.capability.CapabilityType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LightsData extends CapabilityDataWithState {

    @JsonProperty(JsonCapNames.INTENSITY)
    protected Byte intensity;

    @JsonProperty(JsonCapNames.MIN_INTENSITY)
    protected Byte minIntensity;

    @JsonCreator
    public LightsData(@JsonProperty(JsonCapNames.STATE) boolean state,
                      @JsonProperty(JsonCapNames.INTENSITY) Byte intensity,
                      @JsonProperty(JsonCapNames.MIN_INTENSITY) Byte minIntensity) {
        super(state);
        this.intensity = intensity;
        this.minIntensity = minIntensity;
    }

    public Byte getIntensity() {
        return intensity;
    }

    public Byte getMinIntensity() {
        return minIntensity;
    }

    public void setIntensity(Byte intensity) {
        this.intensity = intensity;
    }

    public void setMinIntensity(Byte minIntensity) {
        this.minIntensity = minIntensity;
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof LightCapModel) {
            super.editModel(model);
            LightCapModel result = (LightCapModel) model;
            result.setType(CapabilityType.LIGHT);
            result.setIntensity(this.getIntensity());
            result.setMinIntensity(this.getMinIntensity());
            return result;
        }
        return null;
    }

    public static LightsData fromJson(String json) {
        return SerializeUtils.fromJson(json, LightsData.class);
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.extern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithState;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.extern.ExternBtnCapModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternBtnData extends CapabilityDataWithState {

    @JsonCreator
    public ExternBtnData(@JsonProperty(JsonCapNames.STATE) boolean isTurnedOn) {
        super(isTurnedOn);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof ExternBtnCapModel) {
            super.editModel(model);
            return model;
        }
        return null;
    }

    public static ExternBtnData fromJson(String json) {
        return SerializeUtils.fromJson(json, ExternBtnData.class);
    }
}

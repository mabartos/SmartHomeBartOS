/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasState;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CapabilityDataWithState extends CapabilityData implements HasState {

    @JsonProperty("isTurnedOn")
    protected boolean isTurnedOn;

    @JsonCreator
    public CapabilityDataWithState(@JsonProperty("isTurnedOn") boolean isTurnedOn) {
        super();
        this.isTurnedOn = isTurnedOn;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void setState(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public void changeState() {
        this.isTurnedOn = !this.isTurnedOn;
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof HasState) {
            ((HasState) model).setState(isTurnedOn());
        }
        return model;
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.model.capability.HasState;

public class CapDataWithValueAndState<Type> extends CapabilityDataWithValue<Type> implements HasState {

    private CapabilityDataWithState capState;

    public CapDataWithValueAndState(@JsonProperty(JsonCapNames.ACTUAL_VALUE) Type actual,
                                    @JsonProperty(JsonCapNames.STATE) boolean isTurnedOn) {
        super(actual);
        this.capState = new CapabilityDataWithState(isTurnedOn);
    }

    public boolean isTurnedOn() {
        return capState.isTurnedOn();
    }

    @Override
    public void setState(boolean state) {
        capState.setState(state);
    }

    @Override
    public void changeState() {
        capState.changeState();
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasValue;

public class CapabilityDataWithValue<Type> extends CapabilityData implements HasValue<Type> {

    @JsonProperty(JsonCapNames.ACTUAL_VALUE)
    protected Type actual;

    @JsonCreator
    public CapabilityDataWithValue(@JsonProperty(JsonCapNames.ACTUAL_VALUE) Type actual) {
        super();
        this.actual = actual;
    }

    @Override
    public Type getValue() {
        return actual;
    }

    @Override
    public void setValue(Type value) {
        this.actual = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof HasValue) {
            ((HasValue<Type>) model).setValue(getValue());
        }
        return model;
    }
}

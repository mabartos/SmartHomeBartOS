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
import org.mabartos.api.service.capability.HasNumberValue;

public class CapDataWithNumberValue<Type extends Number> extends CapabilityData implements HasNumberValue<Type> {

    @JsonProperty(JsonCapNames.ACTUAL_VALUE)
    protected Type actual;

    @JsonCreator
    public CapDataWithNumberValue(@JsonProperty(JsonCapNames.ACTUAL_VALUE) Type actual) {
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
        if (model instanceof HasNumberValue) {
            ((HasNumberValue<Type>) model).setValue(getValue());
        }
        return model;
    }
}

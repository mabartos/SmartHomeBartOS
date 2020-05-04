package org.mabartos.api.data.general.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.HasValue;

public abstract class CapabilityDataWithValue<Type> extends CapabilityData implements HasValue<Type> {

    @JsonProperty("actual")
    protected Type actual;

    @JsonCreator
    public CapabilityDataWithValue(@JsonProperty("actual") Type actual) {
        super();
        this.actual = actual;
    }

    public Type getActual() {
        return actual;
    }

    public void setActual(Type actual) {
        this.actual = actual;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof HasValue) {
            ((HasValue<Type>) model).setValue(getActual());
        }
        return model;
    }
}

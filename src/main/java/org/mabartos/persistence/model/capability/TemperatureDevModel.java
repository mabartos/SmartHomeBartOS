package org.mabartos.persistence.model.capability;

import org.mabartos.persistence.model.capability.common.CapabilityWithValue;

import javax.persistence.Entity;

@Entity
public class TemperatureDevModel extends CapabilityWithValue {

    public TemperatureDevModel(){
    }

    public TemperatureDevModel(String name) {
        super(name);
    }

}

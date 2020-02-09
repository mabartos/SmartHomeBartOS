package org.mabartos.persistence.model.capability;

import org.mabartos.persistence.model.capability.common.CapabilityWithValue;

import javax.persistence.Entity;

@Entity
public class HumidityDevModel extends CapabilityWithValue {
    public HumidityDevModel(){
        super();
    }
    public HumidityDevModel(String name) {
        super(name);
    }
}

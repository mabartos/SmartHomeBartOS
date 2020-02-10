package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.common.CapabilityWithValue;

import javax.persistence.Entity;

@Entity
public class HumidityCapModel extends CapabilityWithValue {
    public HumidityCapModel() {
        super();
    }

    public HumidityCapModel(String name) {
        super(name, CapabilityType.HUMIDITY);
    }
}

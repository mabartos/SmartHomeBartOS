package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.common.CapabilityWithValue;

import javax.persistence.Entity;

@Entity
public class TemperatureCapModel extends CapabilityWithValue {

    public TemperatureCapModel() {
    }

    public TemperatureCapModel(String name) {
        super(name, CapabilityType.TEMPERATURE);
        setUnits("°C");
    }

}

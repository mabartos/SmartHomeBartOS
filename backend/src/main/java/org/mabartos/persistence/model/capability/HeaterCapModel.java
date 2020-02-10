package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.common.CapabilityWithValue;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterCapModel extends CapabilityWithValue {

    @Column
    private Double destTemperature;

    public HeaterCapModel() {
    }

    public HeaterCapModel(String name) {
        super(name, CapabilityType.HEATER);
    }

    public Double getDestTemperature() {
        return destTemperature;
    }

    public void setDestTemperature(Double destTemperature) {
        this.destTemperature = destTemperature;
    }
}

package org.mabartos.persistence.model.capability;

import org.mabartos.persistence.model.capability.common.CapabilityWithValue;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterDevModel extends CapabilityWithValue {

    @Column
    private Double destTemperature;

    public HeaterDevModel(){}

    public HeaterDevModel(String name) {
        super(name);
    }

    public Double getDestTemperature() {
        return destTemperature;
    }

    public void setDestTemperature(Double destTemperature) {
        this.destTemperature = destTemperature;
    }
}

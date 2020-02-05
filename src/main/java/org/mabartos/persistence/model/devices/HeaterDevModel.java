package org.mabartos.persistence.model.devices;

import org.mabartos.persistence.model.devices.common.DeviceWithValue;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterDevModel extends DeviceWithValue {

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

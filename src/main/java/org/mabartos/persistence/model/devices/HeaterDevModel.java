package org.mabartos.persistence.model.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.common.DeviceWithValue;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterDevModel extends DeviceWithValue {

    @Column
    private Double destTemperature;

    public HeaterDevModel(){}

    public HeaterDevModel(String name) {
        super(name, DeviceType.HEATER);
    }

    public Double getDestTemperature() {
        return destTemperature;
    }

    public void setDestTemperature(Double destTemperature) {
        this.destTemperature = destTemperature;
    }
}

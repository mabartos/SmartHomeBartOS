package org.mabartos.persistence.model.devices.common;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.devices.HasValue;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DeviceWithValue extends DeviceModel implements HasValue {

    @Column
    private Double value;

    @Column
    private String units;

    public DeviceWithValue(String name, DeviceType type) {
        super(name, type);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;

    }

    @Override
    public String getUnits() {
        return units;
    }

    @Override
    public void setUnits(String units) {
        this.units = units;
    }
}

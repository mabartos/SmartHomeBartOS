package org.mabartos.persistence.model.capability.common;

import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.capability.HasValue;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CapabilityWithValue extends DeviceModel implements HasValue {

    @Column
    private Double value;

    @Column
    private String units;

    public CapabilityWithValue() {
        super();
    }

    public CapabilityWithValue(String name) {
        super(name);
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

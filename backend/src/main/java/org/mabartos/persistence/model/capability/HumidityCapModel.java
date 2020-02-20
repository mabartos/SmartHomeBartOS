package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HumidityCapModel extends CapabilityModel implements HasValue {

    @Column
    private Double value;

    @Column
    private String units;

    public HumidityCapModel() {
        super();
    }

    public HumidityCapModel(String name) {
        super(name, CapabilityType.HUMIDITY);
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

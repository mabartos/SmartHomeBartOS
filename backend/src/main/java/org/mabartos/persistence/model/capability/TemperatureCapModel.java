package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TemperatureCapModel extends CapabilityModel implements HasValueAndUnits<Double> {

    @Column
    private Double value;

    @Column
    private String units;

    public TemperatureCapModel() {
    }

    public TemperatureCapModel(String name, Integer pin) {
        super(name, CapabilityType.TEMPERATURE, pin);
        setUnits("°C");
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
        return this.units;
    }

    @Override
    public void setUnits(String units) {
        this.units = units;
    }

}

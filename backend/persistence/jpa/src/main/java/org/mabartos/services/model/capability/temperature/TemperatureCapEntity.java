package org.mabartos.services.model.capability.temperature;

import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.temperature.TemperatureCapModel;
import org.mabartos.services.model.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TemperatureCapEntity extends CapabilityEntity implements TemperatureCapModel {

    @Column
    private Double value;

    @Column
    private String units;

    public TemperatureCapEntity() {
    }

    public TemperatureCapEntity(String name, Integer pin) {
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

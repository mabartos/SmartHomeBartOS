/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.temperature;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.temperature.TemperatureCapModel;
import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

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

    public TemperatureCapEntity(CapabilityModel model) {
        this(model.getName(), model.getPin());
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        if (checkValidValue(value))
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

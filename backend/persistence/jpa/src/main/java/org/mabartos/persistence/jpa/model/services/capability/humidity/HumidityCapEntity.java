/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.humidity;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.humidity.HumidityCapModel;
import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HumidityCapEntity extends CapabilityEntity implements HumidityCapModel {

    @Column
    private Byte value;

    @Column
    private String units = "%";

    public HumidityCapEntity() {
        super();
    }

    public HumidityCapEntity(String name, Integer pin) {
        super(name, CapabilityType.HUMIDITY, pin);
    }

    public HumidityCapEntity(CapabilityModel model) {
        this(model.getName(), model.getPin());
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public void setValue(Byte value) {
        if (checkValidValue(value)) {
            this.value = value;
        }
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

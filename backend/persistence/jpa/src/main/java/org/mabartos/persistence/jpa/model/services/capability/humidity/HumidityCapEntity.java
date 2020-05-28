/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.humidity;

import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.humidity.HumidityCapModel;
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

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public void setValue(Byte value) {
        if (value >= 0 && value <= 100) {
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

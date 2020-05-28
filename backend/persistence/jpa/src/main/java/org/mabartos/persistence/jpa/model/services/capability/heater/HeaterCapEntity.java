/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.heater;

import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.heater.HeaterCapModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterCapEntity extends CapabilityEntity implements HeaterCapModel {

    @Column
    private Double destTemperature;

    @Column
    private boolean isTurnedOn;

    public HeaterCapEntity() {
    }

    public HeaterCapEntity(String name, Integer pin) {
        super(name, CapabilityType.HEATER, pin);
    }

    @Override
    public Double getDestTemperature() {
        return destTemperature;
    }

    @Override
    public void setDestTemperature(Double destTemperature) {
        this.destTemperature = destTemperature;
    }

    @Override
    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    @Override
    public void setState(boolean state) {
        this.isTurnedOn = state;
    }

    @Override
    public void changeState() {
        this.isTurnedOn = !this.isTurnedOn;
    }
}

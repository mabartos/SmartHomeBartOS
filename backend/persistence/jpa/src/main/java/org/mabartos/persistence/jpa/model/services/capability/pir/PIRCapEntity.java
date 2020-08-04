/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.pir;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.pir.PIRCapModel;
import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PIRCapEntity extends CapabilityEntity implements PIRCapModel {

    @Column
    private boolean isTurnedOn = false;

    public PIRCapEntity() {
    }

    public PIRCapEntity(String name, Integer pin) {
        super(name, CapabilityType.PIR, pin);
    }

    public PIRCapEntity(CapabilityModel model) {
        this(model.getName(), model.getPin());
    }

    @Override
    @JsonProperty(JsonCapNames.STATE)
    public boolean isTurnedOn() {
        return this.isTurnedOn;
    }

    @Override
    public void setState(boolean state) {
        this.isTurnedOn = state;
    }

    @Override
    public void changeState() {
        isTurnedOn = !isTurnedOn;
    }
}

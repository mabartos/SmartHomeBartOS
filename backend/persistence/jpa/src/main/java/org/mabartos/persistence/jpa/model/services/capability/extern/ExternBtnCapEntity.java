/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.extern;

import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.extern.ExternBtnCapModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ExternBtnCapEntity extends CapabilityEntity implements ExternBtnCapModel {

    @Column
    private boolean isTurnedOn;

    @Column
    private boolean hasTwoStates;

    public ExternBtnCapEntity() {
    }

    public ExternBtnCapEntity(String name, Integer pin) {
        super(name, CapabilityType.EXTERN_BTN, pin);
    }

    public ExternBtnCapEntity(CapabilityModel model) {
        this(model.getName(), model.getPin());
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
        isTurnedOn = !isTurnedOn;
    }

    @Override
    public boolean hasTwoStates() {
        return hasTwoStates;
    }

    @Override
    public void setHasTwoStates(boolean state) {
        this.hasTwoStates = state;
    }
}

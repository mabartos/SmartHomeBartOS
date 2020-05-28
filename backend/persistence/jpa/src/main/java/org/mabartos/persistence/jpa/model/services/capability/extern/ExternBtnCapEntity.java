/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.extern;

import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.extern.ExternBtnCapModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ExternBtnCapEntity extends CapabilityEntity implements ExternBtnCapModel {

    @Column
    private boolean isTurnedOn;

    public ExternBtnCapEntity() {
    }

    public ExternBtnCapEntity(String name, Integer pin) {
        super(name, CapabilityType.EXTERN_BTN, pin);
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
}

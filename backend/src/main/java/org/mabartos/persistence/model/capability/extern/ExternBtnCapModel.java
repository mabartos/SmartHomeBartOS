package org.mabartos.persistence.model.capability.extern;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.HasState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ExternBtnCapModel extends CapabilityModel implements HasState {

    @Column
    private boolean isTurnedOn;

    public ExternBtnCapModel() {
    }

    public ExternBtnCapModel(String name, Integer pin) {
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

package org.mabartos.persistence.model.capability.relay;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.HasState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class RelayCapModel extends CapabilityModel implements HasState {

    @Column
    private boolean isTurnedOn = false;

    public RelayCapModel() {
    }

    public RelayCapModel(String name, Integer pin) {
        super(name, CapabilityType.RELAY, pin);
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

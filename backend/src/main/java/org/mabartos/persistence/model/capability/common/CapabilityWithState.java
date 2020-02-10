package org.mabartos.persistence.model.capability.common;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.HasState;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CapabilityWithState extends CapabilityModel implements HasState {

    @Column
    private boolean state;

    public CapabilityWithState() {
    }

    public CapabilityWithState(String name, CapabilityType type) {
        super(name, type);
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void changeState() {
        this.state = !this.state;
    }
}

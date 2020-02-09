package org.mabartos.persistence.model.capability.common;

import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.capability.HasState;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CapabilityWithState extends DeviceModel implements HasState {

    @Column
    private boolean state;

    public CapabilityWithState() {
    }

    public CapabilityWithState(String name) {
        super(name);
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

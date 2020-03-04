package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.protocols.mqtt.data.capability.State;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterCapModel extends CapabilityModel implements HasState {

    @Column
    private Double destTemperature;

    @Column
    private State state;

    public HeaterCapModel() {
    }

    public HeaterCapModel(String name) {
        super(name, CapabilityType.HEATER);
    }

    public Double getDestTemperature() {
        return destTemperature;
    }

    public void setDestTemperature(Double destTemperature) {
        this.destTemperature = destTemperature;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void changeState() {
        state = State.changeState(state);
    }
}

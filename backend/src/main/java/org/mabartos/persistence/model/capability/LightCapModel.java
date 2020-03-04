package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.protocols.mqtt.data.capability.State;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightCapModel extends CapabilityModel implements HasState {

    @Column
    private Double intensity = 0.0;

    @Column
    private Double minIntensity = 0.0;

    @Column
    private State state;

    public LightCapModel() {
    }

    public LightCapModel(String name) {
        super(name, CapabilityType.LIGHT);
    }

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    public Double getMinIntensity() {
        return minIntensity;
    }

    public void setMinIntensity(Double minIntensity) {
        this.minIntensity = minIntensity;
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
        this.state = State.changeState(state);
    }
}

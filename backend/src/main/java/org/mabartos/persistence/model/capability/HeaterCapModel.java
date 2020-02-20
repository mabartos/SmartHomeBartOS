package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HeaterCapModel extends CapabilityModel implements HasState {

    @Column
    private Double destTemperature;

    @Column
    private boolean state;

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
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void changeState() {
        state = !state;
    }
}

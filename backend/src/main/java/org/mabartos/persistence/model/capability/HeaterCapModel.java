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
    private boolean isTurnedOn;

    public HeaterCapModel() {
    }

    public HeaterCapModel(String name, Integer pin) {
        super(name, CapabilityType.HEATER, pin);
    }

    public Double getDestTemperature() {
        return destTemperature;
    }

    public void setDestTemperature(Double destTemperature) {
        this.destTemperature = destTemperature;
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
        this.isTurnedOn = !this.isTurnedOn;
    }
}

package models.capability;


import models.capability.utils.CapabilityType;
import models.capability.utils.HasState;

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

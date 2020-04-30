package org.mabartos.persistence.model.capability.pir;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.HasState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PIRCapModel extends CapabilityModel implements HasState {

    @Column
    private boolean isTurnedOn = false;

    public PIRCapModel() {
    }

    public PIRCapModel(String name, Integer pin) {
        super(name, CapabilityType.PIR, pin);
    }

    @Override
    @JsonProperty("isTurnedOn")
    public boolean isTurnedOn() {
        return this.isTurnedOn;
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

package org.mabartos.persistence.model.capability;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightCapModel extends CapabilityModel implements HasState {

    @Column
    private Byte intensity = 0;

    @Column
    private Byte minIntensity = 0;

    @Column
    private boolean isTurnedOn = false;

    public LightCapModel() {
    }

    public LightCapModel(String name, Integer pin) {
        super(name, CapabilityType.LIGHT, pin);
    }

    public Byte getIntensity() {
        return intensity;
    }

    public void setIntensity(Byte intensity) {
        this.intensity = intensity;
    }

    public Byte getMinIntensity() {
        return minIntensity;
    }

    public void setMinIntensity(Byte minIntensity) {
        this.minIntensity = minIntensity;
    }

    @Override
    @JsonProperty("isTurnedOn")
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

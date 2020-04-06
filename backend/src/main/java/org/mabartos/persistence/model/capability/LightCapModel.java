package org.mabartos.persistence.model.capability;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightCapModel extends CapabilityModel implements HasState {

    @Column
    private Double intensity = 0.0;

    @Column
    private Double minIntensity = 0.0;

    @Column
    private boolean isTurnedOn = false;

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

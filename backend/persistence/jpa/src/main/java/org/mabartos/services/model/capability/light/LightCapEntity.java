package org.mabartos.services.model.capability.light;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.light.LightCapModel;
import org.mabartos.services.model.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightCapEntity extends CapabilityEntity implements LightCapModel {

    @Column
    private Byte intensity = 0;

    @Column
    private Byte minIntensity = 0;

    @Column
    private boolean isTurnedOn = false;

    public LightCapEntity() {
    }

    public LightCapEntity(String name, Integer pin) {
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

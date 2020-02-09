package org.mabartos.persistence.model.capability;

import org.mabartos.persistence.model.capability.common.CapabilityWithState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightDevModel extends CapabilityWithState {

    @Column
    private Double intensity = 0.0;

    @Column
    private Double minIntensity = 0.0;

    public LightDevModel(){}

    public LightDevModel(String name) {
        super(name);
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
}

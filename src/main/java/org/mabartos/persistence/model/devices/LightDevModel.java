package org.mabartos.persistence.model.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.common.DeviceWithState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightDevModel extends DeviceWithState {

    @Column
    private Double intensity = 0.0;

    @Column
    private Double minIntensity = 0.0;

    public LightDevModel() {
        setType(DeviceType.LIGHT);
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

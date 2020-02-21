package models.capability;

import models.capability.utils.CapabilityType;
import models.capability.utils.HasState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class LightCapModel extends CapabilityModel implements HasState {

    @Column
    private Double intensity = 0.0;

    @Column
    private Double minIntensity = 0.0;

    @Column
    private Boolean state;

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
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void changeState() {
        this.state = !this.state;
    }
}

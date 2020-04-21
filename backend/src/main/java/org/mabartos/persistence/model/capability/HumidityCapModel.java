package org.mabartos.persistence.model.capability;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HumidityCapModel extends CapabilityModel implements HasValue<Byte> {

    @Column
    private Byte value;

    @Column
    private String units = "%";

    public HumidityCapModel() {
        super();
    }

    public HumidityCapModel(String name, Integer pin) {
        super(name, CapabilityType.HUMIDITY, pin);
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public void setValue(Byte value) {
        if (value >= 0 && value <= 100) {
            this.value = value;
        }
    }

    @Override
    public String getUnits() {
        return units;
    }

    @Override
    public void setUnits(String units) {
        this.units = units;
    }
}

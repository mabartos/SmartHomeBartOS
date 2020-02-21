package models.capability;

import models.capability.utils.CapabilityType;
import models.capability.utils.HasValue;
import utils.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TemperatureCapModel extends CapabilityModel implements Identifiable, HasValue {

    @Column
    private Double value;

    @Column
    private String units;

    public TemperatureCapModel() {
    }

    public TemperatureCapModel(String name) {
        super(name, CapabilityType.TEMPERATURE);
        setUnits("°C");
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String getUnits() {
        return this.units;
    }

    @Override
    public void setUnits(String units) {
        this.units = units;
    }

}

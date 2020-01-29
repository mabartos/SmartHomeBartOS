package org.mabartos.persistence.model.devices;

public interface HasValue {

    Double getValue();

    void setValue(Double value);

    String getUnits();

    void setUnits(String units);
}

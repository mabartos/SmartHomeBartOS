package org.mabartos.persistence.model.capability;

public interface HasValue {

    Double getValue();

    void setValue(Double value);

    String getUnits();

    void setUnits(String units);
}

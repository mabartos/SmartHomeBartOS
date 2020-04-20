package org.mabartos.persistence.model.capability;

public interface HasValue<T> {

    T getValue();

    void setValue(T value);

    String getUnits();

    void setUnits(String units);
}

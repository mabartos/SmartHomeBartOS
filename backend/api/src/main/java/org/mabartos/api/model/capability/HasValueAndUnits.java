package org.mabartos.api.model.capability;

public interface HasValueAndUnits<T> extends HasValue<T> {

    String getUnits();

    void setUnits(String units);
}

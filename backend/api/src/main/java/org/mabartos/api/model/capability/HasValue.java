package org.mabartos.api.model.capability;

public interface HasValue<T> {
    T getValue();

    void setValue(T value);
}

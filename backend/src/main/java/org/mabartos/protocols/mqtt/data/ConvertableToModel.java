package org.mabartos.protocols.mqtt.data;

public interface ConvertableToModel<T> {
    T toModel();
}

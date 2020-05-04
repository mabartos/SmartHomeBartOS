package org.mabartos.api.data.mqtt;

public interface MqttSerializable {

    default String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }
}

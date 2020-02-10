package org.mabartos.streams.mqtt.messages;

import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public interface MqttSerializable {

    default String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }
}

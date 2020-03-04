package org.mabartos.protocols.mqtt.data;

import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public interface MqttSerializable {

    default String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }
}

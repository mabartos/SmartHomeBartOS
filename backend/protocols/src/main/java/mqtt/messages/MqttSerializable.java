package mqtt.messages;


import mqtt.utils.MqttSerializeUtils;

public interface MqttSerializable {

    default String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }
}

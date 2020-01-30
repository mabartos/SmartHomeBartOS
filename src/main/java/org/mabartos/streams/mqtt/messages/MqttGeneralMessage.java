package org.mabartos.streams.mqtt.messages;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class MqttGeneralMessage implements MqttSerializable {

    private Long id;
    private String name;
    private String topic;
    private DeviceType type;

    public MqttGeneralMessage(Long id, String name, String topic, DeviceType type) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.type = type;
    }

    public MqttGeneralMessage(DeviceModel device) {
        if (device != null) {
            this.id = device.getID();
            this.name = device.getName();
            this.topic = device.getTopic();
            this.type = device.getType();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    @Override
    public String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }

    public static MqttGeneralMessage fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, MqttGeneralMessage.class);
    }
}

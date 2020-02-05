package org.mabartos.streams.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class MqttGeneralMessage implements MqttSerializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("type")
    private CapabilityType type;

    @JsonCreator
    public MqttGeneralMessage(@JsonProperty("id") Long id,
                              @JsonProperty("name") String name,
                              @JsonProperty("topic") String topic,
                              @JsonProperty("type") CapabilityType type) {
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

    public CapabilityType getType() {
        return type;
    }

    public void setType(CapabilityType type) {
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

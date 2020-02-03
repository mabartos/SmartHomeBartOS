package org.mabartos.streams.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.DeviceType;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class MqttAddDeviceMessage implements MqttSerializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private DeviceType type;

    @JsonCreator
    public MqttAddDeviceMessage(
            @JsonProperty("name") String name,
            @JsonProperty("type") DeviceType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static MqttAddDeviceMessage fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, MqttAddDeviceMessage.class);
    }
}

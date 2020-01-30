package org.mabartos.streams.mqtt.messages;

import org.mabartos.general.DeviceType;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class MqttAddDeviceMessage implements MqttSerializable {

    private String name;
    private DeviceType type;

    public MqttAddDeviceMessage(String name, DeviceType type) {
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

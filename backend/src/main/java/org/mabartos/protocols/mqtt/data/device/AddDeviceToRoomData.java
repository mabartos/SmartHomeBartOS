package org.mabartos.protocols.mqtt.data.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.protocols.mqtt.data.general.MqttSerializable;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonPropertyOrder({"roomID", "deviceID"})
public class AddDeviceToRoomData implements ResponseData, MqttSerializable {

    @JsonProperty("resp")
    private boolean response;
    @JsonProperty("roomID")
    private Long roomID;
    @JsonProperty("deviceID")
    private Long deviceID;

    @JsonCreator
    public AddDeviceToRoomData(@JsonProperty("roomID") Long roomID,
                               @JsonProperty("deviceID") Long deviceID) {
        this.roomID = roomID;
        this.deviceID = deviceID;
        this.response = true;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public static AddDeviceToRoomData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, AddDeviceToRoomData.class);
    }

    @Override
    public boolean isResponse() {
        return this.response;
    }

    @Override
    public void setIsResponse(boolean state) {
        //NOP
    }
}

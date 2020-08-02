/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializableJSON;
import org.mabartos.api.data.general.SerializeUtils;

@JsonPropertyOrder({JsonPropertyNames.RESPONSE, JsonPropertyNames.ROOM_ID, JsonPropertyNames.DEVICE_ID})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddDeviceToRoomData implements ResponseData, SerializableJSON {

    @JsonProperty(JsonPropertyNames.RESPONSE)
    private boolean response;
    @JsonProperty(JsonPropertyNames.ROOM_ID)
    private Long roomID;
    @JsonProperty(JsonPropertyNames.DEVICE_ID)
    private Long deviceID;

    @JsonCreator
    public AddDeviceToRoomData(@JsonProperty(JsonPropertyNames.ROOM_ID) Long roomID,
                               @JsonProperty(JsonPropertyNames.DEVICE_ID) Long deviceID,
                               @JsonProperty(JsonPropertyNames.RESPONSE) boolean response) {
        this(deviceID, response);
        this.roomID = roomID;
    }

    @JsonCreator
    public AddDeviceToRoomData(@JsonProperty(JsonPropertyNames.DEVICE_ID) Long deviceID,
                               @JsonProperty(JsonPropertyNames.RESPONSE) boolean response) {
        this.deviceID = deviceID;
        this.response = response;
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
        return SerializeUtils.fromJson(json, AddDeviceToRoomData.class);
    }

    @Override
    public boolean isResponse() {
        return this.response;
    }

    @Override
    public void setIsResponse(boolean state) {
        this.response = state;
    }
}

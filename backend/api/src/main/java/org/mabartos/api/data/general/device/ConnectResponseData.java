/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.device.DeviceModel;

@JsonPropertyOrder({JsonPropertyNames.MESSAGE_ID, JsonPropertyNames.RESPONSE, JsonPropertyNames.ID, JsonPropertyNames.NAME, JsonPropertyNames.ROOM_ID})
public class ConnectResponseData extends DeviceData {

    @JsonProperty(JsonPropertyNames.ROOM_ID)
    private Long roomID;

    public ConnectResponseData(Long msgID, DeviceModel device) {
        super(msgID, device, true);
        this.roomID = device.getRoomID();
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }
}

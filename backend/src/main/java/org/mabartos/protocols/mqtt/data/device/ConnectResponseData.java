package org.mabartos.protocols.mqtt.data.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.DeviceModel;

public class ConnectResponseData extends DeviceData {

    @JsonProperty("roomID")
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

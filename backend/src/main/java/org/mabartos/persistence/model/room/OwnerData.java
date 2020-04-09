package org.mabartos.persistence.model.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.protocols.mqtt.data.MqttSerializable;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.UUID;

public class OwnerData implements MqttSerializable {

    @JsonProperty("ownerID")
    private String ownerID;

    @JsonCreator
    public OwnerData(@JsonProperty("ownerID") String ownerID) {
        this.ownerID = ownerID;
    }

    public UUID getOwnerID() {
        try {
            return UUID.fromString(ownerID);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void setOwnerID(UUID ownerID) {
        this.ownerID = ownerID.toString();
    }

    public OwnerData fromJSON(String JSON) {
        return MqttSerializeUtils.fromJson(JSON, OwnerData.class);
    }
}

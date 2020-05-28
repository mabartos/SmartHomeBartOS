/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializableJSON;
import org.mabartos.api.data.general.SerializeUtils;

import java.util.UUID;

public class OwnerData implements SerializableJSON {

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
        return SerializeUtils.fromJson(JSON, OwnerData.class);
    }
}

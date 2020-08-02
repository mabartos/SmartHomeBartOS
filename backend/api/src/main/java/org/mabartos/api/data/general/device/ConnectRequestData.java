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
import org.mabartos.api.data.general.SerializableJSON;
import org.mabartos.api.data.general.SerializeUtils;

@JsonPropertyOrder({"msgID", "id", "name"})
public class ConnectRequestData implements SerializableJSON {

    @JsonProperty(JsonPropertyNames.MESSAGE_ID)
    private Long msgID;

    @JsonProperty(JsonPropertyNames.ID)
    private Long id;

    @JsonProperty(JsonPropertyNames.NAME)
    private String name;

    public ConnectRequestData(@JsonProperty(JsonPropertyNames.MESSAGE_ID) Long msgID,
                              @JsonProperty(JsonPropertyNames.ID) Long id,
                              @JsonProperty(JsonPropertyNames.NAME) String name) {
        this.msgID = msgID;
        this.id = id;
        this.name = name;
    }

    public Long getMsgID() {
        return msgID;
    }

    public void setMsgID(Long msgID) {
        this.msgID = msgID;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ConnectRequestData fromJson(String json) {
        return SerializeUtils.fromJson(json, ConnectRequestData.class);
    }
}

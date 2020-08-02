/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializableJSON;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;

import java.util.Set;

public class AddDeviceRequestData implements SerializableJSON {

    @JsonProperty(JsonPropertyNames.MESSAGE_ID)
    private Long msgID;

    @JsonProperty(JsonPropertyNames.NAME)
    private String name;

    @JsonProperty(JsonPropertyNames.CAPABILITIES)
    @JsonDeserialize(as = Set.class, contentAs = CapabilityWholeData.class)
    private Set<CapabilityWholeData> capabilities;

    @JsonCreator
    public AddDeviceRequestData(
            @JsonProperty(JsonPropertyNames.MESSAGE_ID) Long msgID,
            @JsonProperty(JsonPropertyNames.NAME) String name,
            @JsonProperty(JsonPropertyNames.CAPABILITIES) Set<CapabilityWholeData> capabilities) {
        this.msgID = msgID;
        this.name = name;
        this.capabilities = capabilities;
    }

    public Long getMsgID() {
        return msgID;
    }

    public void setMsgID(Long msgID) {
        this.msgID = msgID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CapabilityWholeData> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<CapabilityWholeData> capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public String toJson() {
        return new SerializeUtils(this).toJson();
    }

    public static AddDeviceRequestData fromJson(String json) {
        return SerializeUtils.fromJson(json, AddDeviceRequestData.class);
    }
}

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
import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;
import org.mabartos.api.model.device.DeviceModel;

import java.util.Set;

@JsonPropertyOrder({JsonPropertyNames.MESSAGE_ID, JsonPropertyNames.RESPONSE, JsonPropertyNames.ID, JsonPropertyNames.NAME})
@JsonIgnoreProperties(value = {"response"},ignoreUnknown = true)
public class DeviceData implements ResponseData, SerializableJSON {

    @JsonProperty(JsonPropertyNames.MESSAGE_ID)
    private Long msgID;

    @JsonProperty(JsonPropertyNames.ID)
    private Long id;

    @JsonProperty(JsonPropertyNames.NAME)
    private String name;

    @JsonProperty(JsonPropertyNames.RESPONSE)
    private boolean isResponse;

    @JsonProperty(JsonPropertyNames.CAPABILITIES)
    private Set<CapabilityWholeData> capabilities;

    @JsonCreator
    public DeviceData(@JsonProperty(JsonPropertyNames.MESSAGE_ID) Long msgID,
                      @JsonProperty(JsonPropertyNames.ID) Long id,
                      @JsonProperty(JsonPropertyNames.NAME) String name) {
        this.msgID = msgID;
        this.id = id;
        this.name = name;
    }

    public DeviceData(Long msgID, DeviceModel device) {
        this(msgID, device, false);
    }

    public DeviceData(Long msgID, DeviceModel device, boolean isResponse) {
        this.msgID = msgID;
        this.id = device.getID();
        this.name = device.getName();
        this.capabilities = CapabilityWholeData.fromModel(device.getCapabilities());
        this.isResponse = isResponse;
    }

    @JsonCreator
    public DeviceData(@JsonProperty(JsonPropertyNames.MESSAGE_ID) Long idMessage,
                      @JsonProperty(JsonPropertyNames.ID) Long id,
                      @JsonProperty(JsonPropertyNames.NAME) String name,
                      @JsonProperty(JsonPropertyNames.CAPABILITIES) Set<CapabilityWholeData> capabilities) {
        this(idMessage, id, name);
        this.capabilities = capabilities;
    }

    public Long getMsgID() {
        return msgID;
    }

    public void setMsgID(Long msgID) {
        this.msgID = msgID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static DeviceData fromJson(String json) {
        return SerializeUtils.fromJson(json, DeviceData.class);
    }

    @Override
    public boolean isResponse() {
        return isResponse;
    }

    @Override
    public void setIsResponse(boolean state) {
        this.isResponse = state;
    }
}

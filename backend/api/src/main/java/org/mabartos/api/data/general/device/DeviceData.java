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

@JsonPropertyOrder({JsonPropertyNames.ID, JsonPropertyNames.NAME})
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceData implements SerializableJSON {

    @JsonProperty(JsonPropertyNames.ID)
    private Long id;

    @JsonProperty(JsonPropertyNames.NAME)
    private String name;

    @JsonProperty(JsonPropertyNames.CAPABILITIES)
    private Set<CapabilityWholeData> capabilities;

    @JsonCreator
    public DeviceData(@JsonProperty(JsonPropertyNames.ID) Long id,
                      @JsonProperty(JsonPropertyNames.NAME) String name) {
        this.id = id;
        this.name = name;
    }

    public DeviceData(DeviceModel device) {
        this.id = device.getID();
        this.name = device.getName();
        this.capabilities = CapabilityWholeData.fromModel(device.getCapabilities());
    }

    @JsonCreator
    public DeviceData(@JsonProperty(JsonPropertyNames.ID) Long id,
                      @JsonProperty(JsonPropertyNames.NAME) String name,
                      @JsonProperty(JsonPropertyNames.CAPABILITIES) Set<CapabilityWholeData> capabilities) {
        this(id, name);
        this.capabilities = capabilities;
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
}

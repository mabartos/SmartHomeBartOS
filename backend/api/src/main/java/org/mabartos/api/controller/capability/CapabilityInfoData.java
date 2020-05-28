/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.data.general.SerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CapabilityInfoData {

    @JsonProperty("id")
    protected Long id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("enabled")
    protected boolean enabled;

    @JsonProperty("type")
    protected CapabilityType type;

    @JsonProperty("pin")
    protected Integer pin;

    @JsonProperty("active")
    protected boolean active;

    @JsonProperty("deviceID")
    protected Long deviceID;

    @JsonCreator
    public CapabilityInfoData(@JsonProperty("id") Long id,
                              @JsonProperty("name") String name,
                              @JsonProperty("enabled") boolean enabled,
                              @JsonProperty("type") CapabilityType type,
                              @JsonProperty("pin") Integer pin,
                              @JsonProperty("active") boolean active,
                              @JsonProperty("deviceID") Long deviceID) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.type = type;
        this.pin = pin;
        this.active = active;
        this.deviceID = deviceID;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CapabilityType getType() {
        return type;
    }

    public void setType(CapabilityType type) {
        this.type = type;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public static CapabilityInfoData fromJSON(String JSON) {
        return SerializeUtils.fromJson(JSON, CapabilityInfoData.class);
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.manage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializableJSON;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.mqtt.ConvertableToModel;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.service.capability.CapabilityType;

import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({JsonPropertyNames.ID, JsonPropertyNames.PIN, JsonPropertyNames.TYPE})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CapabilityWholeData implements SerializableJSON, ConvertableToModel {

    @JsonProperty(JsonPropertyNames.ID)
    protected Long id;

    @JsonProperty(JsonPropertyNames.TYPE)
    protected CapabilityType type;

    @JsonProperty(JsonPropertyNames.PIN)
    protected Integer pin;

    @JsonCreator
    public CapabilityWholeData(@JsonProperty(JsonPropertyNames.TYPE) CapabilityType type,
                               @JsonProperty(JsonPropertyNames.PIN) Integer pin) {
        this.type = type;
        this.pin = pin;
    }

    public CapabilityWholeData(Long id, CapabilityType type, Integer pin) {
        this(type, pin);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static Set<CapabilityWholeData> fromModel(Set<CapabilityModel> capabilities) {
        if (capabilities != null) {
            Set<CapabilityWholeData> result = new HashSet<>();
            capabilities.forEach(cap -> result.add(new CapabilityWholeData(cap.getID(), cap.getType(), cap.getPin())));
            return result;
        }
        return null;
    }

    public static CapabilityWholeData fromJson(String json) {
        return SerializeUtils.fromJson(json, CapabilityWholeData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        model.setType(this.getType());
        model.setPin(this.getPin());
        return model;
    }
}

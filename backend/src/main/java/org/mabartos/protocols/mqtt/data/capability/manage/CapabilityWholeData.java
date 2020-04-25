package org.mabartos.protocols.mqtt.data.capability.manage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.common.Capabilities;
import org.mabartos.protocols.mqtt.data.general.ConvertableToModel;
import org.mabartos.protocols.mqtt.data.general.MqttSerializable;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({"id", "pin", "type"})
public class CapabilityWholeData implements MqttSerializable, ConvertableToModel {

    @JsonProperty("id")
    protected Long id;

    @JsonProperty("type")
    protected CapabilityType type;

    @JsonProperty("pin")
    protected Integer pin;

    @JsonCreator
    public CapabilityWholeData(@JsonProperty("type") CapabilityType type,
                               @JsonProperty("pin") Integer pin) {
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

    public static Capabilities toCapabilities(Set<CapabilityWholeData> capabilities) {
        if (capabilities != null) {
            return new Capabilities(toModel(capabilities));
        }
        return null;
    }

    public static Set<CapabilityModel> toModel(Set<CapabilityWholeData> jsonCapabilities) {
        if (jsonCapabilities != null) {
            Set<CapabilityModel> result = new HashSet<>();
            jsonCapabilities.forEach(cap -> result.add(new CapabilityModel(CapabilityUtils.getRandomNameForCap(cap.getType()), cap.getType(), cap.getPin())));
            return result;
        }
        return null;
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
        return MqttSerializeUtils.fromJson(json, CapabilityWholeData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        model.setType(this.getType());
        model.setPin(this.getPin());
        return model;
    }
}

package org.mabartos.protocols.mqtt.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.common.Capabilities;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({"id", "name", "type"})
public class CapabilityData implements MqttSerializable, ConvertableToModel {

    @JsonProperty("id")
    protected Long id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("type")
    protected CapabilityType type;

    @JsonCreator
    public CapabilityData(@JsonProperty("name") String name,
                          @JsonProperty("type") CapabilityType type) {
        this.name = name;
        this.type = type;
    }

    public CapabilityData(Long id, String name, CapabilityType type) {
        this(name, type);
        this.id = id;
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

    public CapabilityType getType() {
        return type;
    }

    public void setType(CapabilityType type) {
        this.type = type;
    }

    public static Capabilities toCapabilities(Set<CapabilityData> capabilities) {
        if (capabilities != null) {
            return new Capabilities(toModel(capabilities));
        }
        return null;
    }

    public static Set<CapabilityModel> toModel(Set<CapabilityData> jsonCapabilities) {
        if (jsonCapabilities != null) {
            Set<CapabilityModel> result = new HashSet<>();
            jsonCapabilities.forEach(cap -> result.add(new CapabilityModel(cap.getName(), cap.getType())));
            return result;
        }
        return null;
    }

    public static Set<CapabilityData> fromModel(Set<CapabilityModel> capabilities) {
        if (capabilities != null) {
            Set<CapabilityData> result = new HashSet<>();
            capabilities.forEach(cap -> result.add(new CapabilityData(cap.getID(), cap.getName(), cap.getType())));
            return result;
        }
        return null;
    }

    public static CapabilityData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, CapabilityData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        model.setName(this.getName());
        model.setType(this.getType());
        return model;
    }
}

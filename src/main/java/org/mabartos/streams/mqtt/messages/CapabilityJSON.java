package org.mabartos.streams.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.devices.common.Capabilities;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

import java.util.ArrayList;
import java.util.List;

public class CapabilityJSON implements MqttSerializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private CapabilityType type;

    @JsonCreator
    public CapabilityJSON(@JsonProperty("id") Integer id,
                          @JsonProperty("name") String name,
                          @JsonProperty("type") CapabilityType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public CapabilityModel toModel() {
        return new CapabilityModel(id, name, type);
    }

    public static Capabilities toCapabilities(List<CapabilityJSON> capabilities) {
        if (capabilities != null) {
            return new Capabilities(toModel(capabilities));
        }
        return null;
    }

    public static List<CapabilityModel> toModel(List<CapabilityJSON> jsonCapabilities) {
        if (jsonCapabilities != null) {
            ArrayList<CapabilityModel> result = new ArrayList<>();
            jsonCapabilities.forEach(cap -> result.add(new CapabilityModel(cap.getId(), cap.getName(), cap.getType())));
            return result;
        }
        return null;
    }

    public static CapabilityJSON fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, CapabilityJSON.class);
    }
}

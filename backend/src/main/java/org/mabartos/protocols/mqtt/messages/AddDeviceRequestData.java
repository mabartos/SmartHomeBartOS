package org.mabartos.protocols.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.Set;

public class AddDeviceRequestData implements MqttSerializable {

    @JsonProperty("idMessage")
    private Long idMessage;

    @JsonProperty("name")
    private String name;

    @JsonProperty("capabilities")
    @JsonDeserialize(as = Set.class, contentAs = CapabilityData.class)
    private Set<CapabilityData> capabilities;

    @JsonCreator
    public AddDeviceRequestData(
            @JsonProperty("idMessage") Long idMessage,
            @JsonProperty("name") String name,
            @JsonProperty("capabilities") Set<CapabilityData> capabilities) {
        this.idMessage = idMessage;
        this.name = name;
        this.capabilities = capabilities;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CapabilityData> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<CapabilityData> capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }

    public static AddDeviceRequestData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, AddDeviceRequestData.class);
    }
}

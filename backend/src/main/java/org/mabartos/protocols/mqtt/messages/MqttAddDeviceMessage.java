package org.mabartos.protocols.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.ArrayList;
import java.util.List;

public class MqttAddDeviceMessage implements MqttSerializable {

    @JsonProperty("idMessage")
    private Long idMessage;

    @JsonProperty("name")
    private String name;

    @JsonProperty("capabilities")
    @JsonDeserialize(as = ArrayList.class, contentAs = CapabilityJSON.class)
    private List<CapabilityJSON> capabilities;

    @JsonCreator
    public MqttAddDeviceMessage(
            @JsonProperty("idMessage") Long idMessage,
            @JsonProperty("name") String name,
            @JsonProperty("capabilities") List<CapabilityJSON> capabilities) {
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

    public List<CapabilityJSON> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<CapabilityJSON> capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }

    public static MqttAddDeviceMessage fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, MqttAddDeviceMessage.class);
    }
}

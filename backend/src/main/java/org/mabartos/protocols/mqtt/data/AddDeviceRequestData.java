package org.mabartos.protocols.mqtt.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.Set;

public class AddDeviceRequestData implements MqttSerializable {

    @JsonProperty("msgID")
    private Long mgsID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("capabilities")
    @JsonDeserialize(as = Set.class, contentAs = CapabilityData.class)
    private Set<CapabilityData> capabilities;

    @JsonCreator
    public AddDeviceRequestData(
            @JsonProperty("msgID") Long msgID,
            @JsonProperty("name") String name,
            @JsonProperty("capabilities") Set<CapabilityData> capabilities) {
        this.mgsID = msgID;
        this.name = name;
        this.capabilities = capabilities;
    }

    public Long getMgsID() {
        return mgsID;
    }

    public void setMgsID(Long mgsID) {
        this.mgsID = mgsID;
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

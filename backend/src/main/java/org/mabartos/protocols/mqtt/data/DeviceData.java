package org.mabartos.protocols.mqtt.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

import java.util.Set;

public class DeviceData implements MqttSerializable {

    @JsonProperty("idMessage")
    private Long idMessage;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("capabilities")
    private Set<CapabilityData> capabilities;

    @JsonCreator
    public DeviceData(@JsonProperty("idMessage") Long idMessage,
                      @JsonProperty("id") Long id,
                      @JsonProperty("name") String name) {
        this.idMessage = idMessage;
        this.id = id;
        this.name = name;
    }

    public DeviceData(Long idMessage, DeviceModel device) {
        this.idMessage = idMessage;
        this.id = device.getID();
        this.name = device.getName();
        this.capabilities = CapabilityData.fromModel(device.getCapabilities());
    }

    @JsonCreator
    public DeviceData(@JsonProperty("idMessage") Long idMessage,
                      @JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("capabilities") Set<CapabilityData> capabilities) {
        this(idMessage, id, name);
        this.capabilities = capabilities;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
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

    public Set<CapabilityData> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<CapabilityData> capabilities) {
        this.capabilities = capabilities;
    }

    public static DeviceData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, DeviceData.class);
    }

    public DeviceModel toModel() {
        DeviceModel created = new DeviceModel(this.name, CapabilityData.toModel(this.capabilities));
        created.setID(this.id);
        return created;
    }
}

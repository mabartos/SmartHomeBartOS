package org.mabartos.protocols.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.DeviceModel;

import java.util.Set;

public class AddDeviceResponseData implements MqttSerializable {

    @JsonProperty("idMessage")
    private Long idMessage;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("capabilities")
    private Set<CapabilityData> capabilities;

    public AddDeviceResponseData(Long idMessage, Long id, String name) {
        this.idMessage = idMessage;
        this.id = id;
        this.name = name;
    }

    public AddDeviceResponseData(Long idMessage, DeviceModel device) {
        this.idMessage = idMessage;
        this.id = device.getID();
        this.name = device.getName();
        this.capabilities = CapabilityData.fromModel(device.getCapabilities());
    }

    public AddDeviceResponseData(Long idMessage, Long id, String name, Set<CapabilityData> capabilities) {
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
}

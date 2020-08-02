/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.mqtt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.device.DeviceModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MqttGeneralData implements MqttSerializable {

    @JsonProperty(JsonCapNames.MESSAGE_ID)
    private Long idMessage;

    @JsonProperty(JsonCapNames.ID)
    private Long id;

    @JsonProperty(JsonCapNames.NAME)
    private String name;

    @JsonProperty(JsonCapNames.TOPIC)
    private String topic;

    @JsonCreator
    public MqttGeneralData(
            @JsonProperty(JsonCapNames.MESSAGE_ID) Long idMessage,
            @JsonProperty(JsonCapNames.ID) Long id,
            @JsonProperty(JsonCapNames.NAME) String name,
            @JsonProperty(JsonCapNames.TOPIC) String topic
    ) {
        this.idMessage = idMessage;
        this.id = id;
        this.name = name;
        this.topic = topic;
    }

    public MqttGeneralData(DeviceModel device, Long idMessage) {
        this.idMessage = idMessage;
        if (device != null) {
            this.id = device.getID();
            this.name = device.getName();
            this.topic = device.getTopic();
        }
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    public String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }

    public static MqttGeneralData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, MqttGeneralData.class);
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.home;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializeUtils;

public class HomeData {

    @JsonProperty(JsonPropertyNames.ID)
    private Long id;

    @JsonProperty(JsonPropertyNames.NAME)
    private String name;

    @JsonProperty(JsonPropertyNames.ACTIVE)
    private boolean active;

    @JsonProperty(JsonPropertyNames.BROKER_URL)
    private String brokerURL;

    @JsonProperty(JsonPropertyNames.MQTT_CLIENT_ID)
    private Long mqttClientID;

    @JsonCreator
    public HomeData(@JsonProperty(JsonPropertyNames.ID) Long id,
                    @JsonProperty(JsonPropertyNames.NAME) String name,
                    @JsonProperty(JsonPropertyNames.BROKER_URL) String brokerURL,
                    @JsonProperty(JsonPropertyNames.MQTT_CLIENT_ID) Long mqttClientID
    ) {
        this.id = id;
        this.name = name;
        this.brokerURL = brokerURL;
        this.mqttClientID = mqttClientID;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public Long getMqttClientID() {
        return mqttClientID;
    }

    public static HomeData fromJson(String json) {
        return SerializeUtils.fromJson(json, HomeData.class);
    }
}

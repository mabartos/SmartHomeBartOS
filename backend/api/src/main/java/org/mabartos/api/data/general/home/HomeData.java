/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.home;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;

public class HomeData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("brokerURL")
    private String brokerURL;

    @JsonProperty("mqttClientID")
    private Long mqttClientID;

    @JsonCreator
    public HomeData(@JsonProperty("id") Long id,
                    @JsonProperty("name") String name,
                    @JsonProperty("brokerURL") String brokerURL,
                    @JsonProperty("mqttClientID") Long mqttClientID
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

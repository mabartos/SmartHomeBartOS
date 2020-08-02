/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializeUtils;

public class RoomData {

    @JsonProperty(JsonPropertyNames.ID)
    private Long id;

    @JsonProperty(JsonPropertyNames.NAME)
    private String name;

    @JsonProperty(JsonPropertyNames.HOME_ID)
    private Long homeID;

    @JsonCreator
    public RoomData(@JsonProperty(JsonPropertyNames.ID) Long id,
                    @JsonProperty(JsonPropertyNames.NAME) String name,
                    @JsonProperty(JsonPropertyNames.HOME_ID) Long homeID) {
        this.id = id;
        this.name = name;
        this.homeID = homeID;
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

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public static RoomData fromJson(String json) {
        return SerializeUtils.fromJson(json, RoomData.class);
    }
}

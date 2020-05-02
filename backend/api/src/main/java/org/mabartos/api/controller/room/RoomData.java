package org.mabartos.api.controller.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;

public class RoomData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("homeID")
    private Long homeID;

    @JsonCreator
    public RoomData(@JsonProperty("id") Long id,
                    @JsonProperty("name") String name,
                    @JsonProperty("homeID") Long homeID) {
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

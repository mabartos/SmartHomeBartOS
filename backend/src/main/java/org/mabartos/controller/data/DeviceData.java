package org.mabartos.controller.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("homeID")
    private Long homeID;

    @JsonProperty("roomID")
    private Long roomID;

    @JsonCreator
    public DeviceData(@JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("active") boolean active,
                      @JsonProperty("topic") String topic,
                      @JsonProperty("homeID") Long homeID,
                      @JsonProperty("roomID") Long roomID) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.topic = topic;
        this.homeID = homeID;
        this.roomID = roomID;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public static DeviceData fromJson(String JSON) {
        return MqttSerializeUtils.fromJson(JSON, DeviceData.class);
    }
}
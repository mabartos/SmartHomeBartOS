/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.topics;

public class DeviceTopic implements GeneralTopic {

    private Long homeID;
    private Long deviceID;

    public DeviceTopic(Long homeID, Long deviceID) {
        this.homeID = homeID;
        this.deviceID = deviceID;
    }

    public DeviceTopic(String homeID, String deviceID) {
        this.homeID = Long.parseLong(homeID);
        this.deviceID = Long.parseLong(deviceID);
    }

    @Override
    public Long getHomeID() {
        return homeID;
    }

    @Override
    public TopicType getTopicType() {
        return TopicType.DEVICE_TOPIC;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }
}

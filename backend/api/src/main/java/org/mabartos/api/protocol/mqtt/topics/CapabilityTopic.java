/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.topics;

import org.mabartos.api.service.capability.CapabilityType;

public class CapabilityTopic implements GeneralTopic {
    private CapabilityType capabilityType;
    private Long homeID;
    private Long roomID;
    private Long capabilityID;

    public CapabilityTopic(CapabilityType capabilityType, Long homeID, Long roomID, Long capabilityID) {
        this.capabilityType = capabilityType;
        this.homeID = homeID;
        this.roomID = roomID;
        this.capabilityID = capabilityID;
    }

    public CapabilityTopic(String capabilityType, String homeID, String roomID, String capabilityID) {
        setCapabilityType(capabilityType);
        this.homeID = Long.parseLong(homeID);
        this.roomID = Long.parseLong(roomID);
        this.capabilityID = Long.parseLong(capabilityID);
    }

    private void setCapabilityType(String capabilityType) {
        try {
            this.capabilityType = CapabilityType.valueOf(capabilityType);
        } catch (IllegalArgumentException e) {
            this.capabilityType = CapabilityType.getByName(capabilityType);
        }
    }

    public CapabilityType getCapabilityType() {
        return capabilityType;
    }

    public Long getRoomID() {
        return roomID;
    }

    public Long getCapabilityID() {
        return capabilityID;
    }

    @Override
    public Long getHomeID() {
        return homeID;
    }

    @Override
    public TopicType getTopicType() {
        return TopicType.CAPABILITY_TOPIC;
    }

}

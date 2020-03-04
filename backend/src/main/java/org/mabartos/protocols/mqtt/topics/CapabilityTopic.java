package org.mabartos.protocols.mqtt.topics;

import org.mabartos.general.CapabilityType;

public class CapabilityTopic implements GeneralTopic {
    private CapabilityType capabilityType;
    private Long homeID;
    private Long deviceID;
    private Long capabilityID;

    public CapabilityTopic(CapabilityType capabilityType, Long homeID, Long deviceID, Long capabilityID) {
        this.capabilityType = capabilityType;
        this.homeID = homeID;
        this.deviceID = deviceID;
        this.capabilityID = capabilityID;
    }

    public CapabilityTopic(String capabilityType, String homeID, String deviceID, String capabilityID) {
        setCapabilityType(capabilityType);
        this.homeID = Long.parseLong(homeID);
        this.deviceID = Long.parseLong(deviceID);
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

    public Long getDeviceID() {
        return deviceID;
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

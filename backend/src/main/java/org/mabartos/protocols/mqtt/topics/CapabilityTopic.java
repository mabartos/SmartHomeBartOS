package org.mabartos.protocols.mqtt.topics;

import org.mabartos.general.CapabilityType;

public class CapabilityTopic implements GeneralTopic {
    private TopicType topicType;
    private CapabilityType capabilityType;
    private Long homeID;
    private Long deviceID;
    private Long capabilityID;
    public static final Integer TOPIC_ITEMS_COUNT = 6;

    public CapabilityTopic(CapabilityType capabilityType, Long homeID, Long deviceID, Long capabilityID) {
        this.capabilityType = capabilityType;
        this.homeID = homeID;
        this.deviceID = deviceID;
        this.capabilityID = capabilityID;
        this.topicType = TopicType.CAPABILITY_TOPIC;
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
        return topicType;
    }

    @Override
    public Integer getTopicItemsCount() {
        return TOPIC_ITEMS_COUNT;
    }
}

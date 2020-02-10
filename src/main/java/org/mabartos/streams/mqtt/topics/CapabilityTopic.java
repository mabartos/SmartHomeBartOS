package org.mabartos.streams.mqtt.topics;

import org.mabartos.general.CapabilityType;

public class CapabilityTopic implements GeneralTopic {
    private TopicType topicType;
    private CapabilityType capabilityType;
    private Long homeID;
    private Long deviceID;
    public static final Integer TOPIC_ITEMS_COUNT = 4;

    public CapabilityTopic(CapabilityType capabilityType, Long homeID, Long deviceID) {
        this.capabilityType = capabilityType;
        this.homeID = homeID;
        this.deviceID = deviceID;
        this.topicType = TopicType.CAPABILITY_TOPIC;
    }

    public CapabilityType getCapabilityType() {
        return capabilityType;
    }

    public Long getDeviceID() {
        return deviceID;
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

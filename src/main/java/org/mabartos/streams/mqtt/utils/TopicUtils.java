package org.mabartos.streams.mqtt.utils;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.streams.mqtt.topics.CRUDTopic;
import org.mabartos.streams.mqtt.topics.CRUDTopicType;
import org.mabartos.streams.mqtt.topics.CapabilityTopic;
import org.mabartos.streams.mqtt.topics.GeneralTopic;
import org.mabartos.streams.mqtt.topics.Topics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TopicUtils {

    // Topic f.e. "/homes/5/temp/3"

    public static String getHomeTopic(HomeModel home) {
        if (home != null) {
            return Topics.HOME_TOPIC.getTopic() + "/" + home.getID();
        }
        return null;
    }


    public static GeneralTopic getSpecificTopic(String topic) {
        try {
            List<String> topics = new ArrayList<>(Arrays.asList(topic.split("/")));
            // Split takes even empty string at beginning
            final int size = topics.size() - 1;

            // /homes/123/add or f.e /homes/123/temp/2
            if (size >= 3 && topics.get(1).equals(Topics.HOME_TOPIC.getName())) {
                Long homeID = Long.parseLong(topics.get(2));
                if (size == CRUDTopic.TOPIC_ITEMS_COUNT) {
                    CRUDTopicType type = CRUDTopicType.getByName(topics.get(3));
                    if (type != null) {
                        return new CRUDTopic(homeID, type);
                    }
                } else if (size == CapabilityTopic.TOPIC_ITEMS_COUNT) {
                    CapabilityType type = CapabilityType.getByName(topics.get(3));
                    if (type != null) {
                        Long deviceID = Long.parseLong(topics.get(4));
                        return new CapabilityTopic(type, homeID, deviceID);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

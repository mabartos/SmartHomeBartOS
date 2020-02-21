package mqtt.utils;

import models.HomeModel;
import models.capability.utils.CapabilityType;
import mqtt.topics.CRUDTopic;
import mqtt.topics.CRUDTopicType;
import mqtt.topics.CapabilityTopic;
import mqtt.topics.GeneralTopic;
import mqtt.topics.Topics;

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

            // /homes/123/add or f.e /homes/123/dev/2/temp/2
            if (size >= 3 && topics.get(1).equals(Topics.HOME_TOPIC.getName())) {
                Long homeID = Long.parseLong(topics.get(2));
                if (size == CRUDTopic.TOPIC_ITEMS_COUNT) {
                    CRUDTopicType type = CRUDTopicType.getByName(topics.get(3));
                    if (type != null) {
                        return new CRUDTopic(homeID, type);
                    }
                } else if (size == CapabilityTopic.TOPIC_ITEMS_COUNT && topics.get(3).equals(Topics.DEVICE_TOPIC.getName())) {
                    Long deviceID = Long.parseLong(topics.get(4));
                    CapabilityType type = CapabilityType.getByName(topics.get(5));
                    if (type != null) {
                        Long capabilityID = Long.parseLong(topics.get(6));
                        return new CapabilityTopic(type, homeID, deviceID, capabilityID);
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

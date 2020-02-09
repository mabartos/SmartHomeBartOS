package org.mabartos.streams.mqtt.utils;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.streams.mqtt.MqttTopics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopicUtils {

    // Topic f.e. "/homes/5/temp/3"

    public static String getHomeTopic(HomeModel home) {
        if (home != null) {
            return MqttTopics.BASIC_TOPIC + "/" + home.getID();
        }
        return null;
    }

    public static class GeneralTopic {
        private Long homeID;
        private CapabilityType type;
        private Long deviceID;

        public static GeneralTopic getGeneralTopic(String topic) {
            try {
                GeneralTopic instance = new GeneralTopic();
                List<String> topics = new ArrayList<>(Arrays.asList(topic.split("/")));
                instance.homeID = Long.parseLong(topics.get(2));
                instance.type = CapabilityType.valueOf(topics.get(3));
                instance.deviceID = Long.parseLong(topics.get(4));
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public Long getHomeID() {
            return homeID;
        }

        public CapabilityType getType() {
            return type;
        }

        public Long getDeviceID() {
            return deviceID;
        }

        public String getHomeTopic() {
            return MqttTopics.BASIC_TOPIC + "/" + homeID;
        }
    }
}

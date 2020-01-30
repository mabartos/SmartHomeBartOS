package org.mabartos.streams.mqtt.utils;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.streams.mqtt.MqttTopics;

public class TopicUtils {

    public static String getTopic(HomeModel home) {
        if (home != null) {
            return MqttTopics.BASIC_TOPIC + "/" + home.getID();
        }
        return null;
    }
}

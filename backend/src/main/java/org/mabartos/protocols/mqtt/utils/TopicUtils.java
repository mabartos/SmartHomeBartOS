package org.mabartos.protocols.mqtt.utils;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.home.HomeModel;
import org.mabartos.protocols.mqtt.topics.CRUDTopic;
import org.mabartos.protocols.mqtt.topics.CRUDTopicType;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;
import org.mabartos.protocols.mqtt.topics.GeneralTopic;
import org.mabartos.protocols.mqtt.topics.Topics;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TopicUtils {

    // Topic f.e. 'homes/5'
    public static String getHomeTopic(HomeModel home) {
        if (home != null) {
            return getHomeTopic(home.getID());
        }
        return null;
    }

    public static String getHomeTopic(Long homeID) {
        return Topics.HOME_TOPIC.getTopic() + "/" + homeID;
    }

    // Topic f.e. 'homes/5/devices/2'
    public static String getDeviceTopic(HomeModel home, DeviceModel device) {
        if(home!=null && device!=null){
            return getDeviceTopic(home.getID(),device.getID());
        }
        return null;
    }

    public static String getDeviceTopic(Long homeID, Long deviceID) {
        return getHomeTopic(homeID)+Topics.DEVICE_TOPIC.getTopic()+"/"+deviceID;
    }

    /**
     * Create specific topic
     * <p>
     * 1. Manage topic CREATE                   homes/5/create
     * 2. Manage topic OTHER with device ID     homes/5/update/2
     * 3. Capability Topic                      homes/5/rooms/12/temp/4
     */
    public static GeneralTopic getSpecificTopic(String topic) {
        try {
            StringBuilder builder = new StringBuilder();
            // Manage topics
            Arrays.stream(CRUDTopicType.values()).forEach(f -> builder.append(f.getName()).append("|"));
            builder.deleteCharAt(builder.length() - 1);
            final String MANAGE = "^homes/(\\d+)/(" + builder.toString() + ")/*(\\d*)$";

            // 1.
            Matcher manageTopic = Pattern.compile(MANAGE).matcher(topic);
            if (manageTopic.matches() && manageTopic.groupCount() >= 2) {
                CRUDTopicType type = CRUDTopicType.getByName(manageTopic.group(2));
                if (type.equals(CRUDTopicType.CREATE) && manageTopic.group(3) != null && !manageTopic.group(3).isEmpty()) {
                    return null;
                }
                return new CRUDTopic(manageTopic.group(1), manageTopic.group(3), type);
            }

            // clear builder
            builder.delete(0, builder.length());
            Arrays.stream(CapabilityType.values()).map(CapabilityType::getName).forEach(item -> builder.append(item).append("|"));

            //General topic
            String GENERAL = "^homes/(\\d+)/rooms/(\\d+)/(" + builder.toString() + ")/(\\d+).*";
            Matcher generalTopic = Pattern.compile(GENERAL).matcher(topic);
            if (generalTopic.matches() && generalTopic.groupCount() > 3) {
                return new CapabilityTopic(generalTopic.group(3), generalTopic.group(1), generalTopic.group(2), generalTopic.group(4));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}

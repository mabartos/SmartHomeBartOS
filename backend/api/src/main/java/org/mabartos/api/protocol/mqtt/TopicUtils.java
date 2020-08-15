/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.protocol.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.api.protocol.mqtt.topics.CapabilityTopic;
import org.mabartos.api.protocol.mqtt.topics.GeneralTopic;
import org.mabartos.api.protocol.mqtt.topics.Topics;
import org.mabartos.api.service.capability.CapabilityType;

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

    public static String getRoomTopic(Long homeID, Long roomID) {
        return getHomeTopic(homeID) + Topics.ROOM_TOPIC.getTopic() + "/" + roomID;
    }

    public static String getDeviceTopicInRoom(Long homeID, Long roomID, Long deviceID) {
        return getRoomTopic(homeID, roomID) + Topics.DEVICE_TOPIC.getTopic() + "/" + deviceID;
    }

    // Topic f.e. 'homes/5/devices/2'
    public static String getDeviceTopic(Long homeID, Long deviceID) {
        return getHomeTopic(homeID) + Topics.DEVICE_TOPIC.getTopic() + "/" + deviceID;
    }

    public static String getCapabilityTopic(CapabilityModel cap) {
        if (cap != null && cap.getDevice() != null && cap.getDevice().getHome() != null && cap.getDevice().getRoom() != null) {
            String roomTopic = getRoomTopic(cap.getDevice().getHomeID(), cap.getDevice().getRoomID());
            return roomTopic + cap.getType().getTopic() + "/" + cap.getID();
        }
        return null;
    }

    public static String getEraseAllDeviceHWTopic(Long homeID, Long deviceID) {
        return getHomeTopic(homeID) + Topics.ERASE_ALL_TOPIC.getTopic() + "/" + deviceID;
    }

    /**
     * Create specific topic
     * <p>
     * 1. Capability Topic Home                      homes/5/temp/4
     * 2. Capability Topic Room                      homes/5/rooms/12/temp/4
     */
    public static GeneralTopic getSpecificTopic(String topic) {
        try {
            StringBuilder builder = new StringBuilder();

            Arrays.stream(CapabilityType.values())
                    .map(CapabilityType::getName)
                    .forEach(item -> builder.append(item).append("|"));

            final String HOME_TOPIC = "^homes/(\\d+)/(" + builder.toString() + ")/(\\d+).*";
            Matcher homeTopic = Pattern.compile(HOME_TOPIC).matcher(topic);
            if (homeTopic.matches() && homeTopic.groupCount() > 2) {
                return new CapabilityTopic(homeTopic.group(2), homeTopic.group(1), "-1", homeTopic.group(3));
            }

            // Room topic
            final String ROOM_TOPIC = "^homes/(\\d+)/rooms/(\\d+)/(" + builder.toString() + ")/(\\d+).*";
            Matcher roomTopic = Pattern.compile(ROOM_TOPIC).matcher(topic);
            if (roomTopic.matches() && roomTopic.groupCount() > 3) {
                return new CapabilityTopic(roomTopic.group(3), roomTopic.group(1), roomTopic.group(2), roomTopic.group(4));
            }
        } catch (RuntimeException e) {
            throw new WrongMessageTopicException();
        }
        return null;
    }
}

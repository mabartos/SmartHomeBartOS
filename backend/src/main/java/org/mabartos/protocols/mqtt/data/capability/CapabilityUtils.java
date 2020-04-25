package org.mabartos.protocols.mqtt.data.capability;

import org.mabartos.general.CapabilityType;

import java.util.Random;

public class CapabilityUtils {

    public static String getRandomNameForCap(CapabilityType type) {
        Random random = new Random();
        String topic = type.getTopic();
        return topic.substring(1) + "_" + random.nextInt(100000);
    }
}

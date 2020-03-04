package org.mabartos.protocols.mqtt.capability;

import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.protocols.mqtt.data.BartMqttSender;
import org.mabartos.protocols.mqtt.data.CapabilityData;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class ParseUtils {
    
    public static void parse(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, CapabilityData data) {
        parse(services, client, capabilityTopic, data, null);
    }

    public static void parse(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, CapabilityData data, String message) {
        if (data != null) {
            CapabilityModel updated = services.capabilities().updateByID(capabilityTopic.getCapabilityID(), data.toModel());
            if (updated != null) {
                BartMqttSender.sendResponse(client, 200, message);
                return;
            }
        }
        BartMqttSender.sendResponse(client, 400, message);
    }
}

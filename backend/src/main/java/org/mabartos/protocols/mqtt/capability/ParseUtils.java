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
        CapabilityModel model = services.capabilities().findByID(capabilityTopic.getCapabilityID());
        if (data != null && model != null) {
            CapabilityModel result = data.editModel(model);
            CapabilityModel updated = services.capabilities().updateByID(capabilityTopic.getCapabilityID(), result);
            if (updated != null) {
                BartMqttSender.sendResponse(client, 200, message);
                return;
            }
        }
        BartMqttSender.sendResponse(client, 400, message);
    }
}

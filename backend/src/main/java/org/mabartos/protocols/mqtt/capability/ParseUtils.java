package org.mabartos.protocols.mqtt.capability;

import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.protocols.mqtt.data.general.BartMqttSender;
import org.mabartos.protocols.mqtt.data.general.ConvertableToModel;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class ParseUtils {

    public static <Data extends ConvertableToModel> void parse(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, Data data) {
        parse(services, client, capabilityTopic, data, null);
    }

    @SuppressWarnings("unchecked")
    public static <Model extends CapabilityModel, Data extends ConvertableToModel> void parse(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, Data data, String message) {
        Model model = (Model) services.capabilities().findByID(capabilityTopic.getCapabilityID());
        if (data != null && model != null) {
            Model result = (Model) data.editModel(model);
            Model updated = (Model) services.capabilities().updateByID(capabilityTopic.getCapabilityID(), result);
            if(updated!=null) return;
        }
        //BartMqttSender.sendResponse(client, 400, message);
    }
}

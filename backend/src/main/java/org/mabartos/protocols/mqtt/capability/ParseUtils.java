package org.mabartos.protocols.mqtt.capability;

import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.protocols.mqtt.data.general.ConvertableToModel;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class ParseUtils {

    @SuppressWarnings("unchecked")
    public static <Model extends CapabilityModel, Data extends ConvertableToModel> void parse(AppServices services, CapabilityTopic capabilityTopic, Data data) {
        Model model = (Model) services.capabilities().findByID(capabilityTopic.getCapabilityID());
        if (data != null && model != null) {
            Model result = (Model) data.editModel(model);
            services.capabilities().updateByID(capabilityTopic.getCapabilityID(), result);
        }
    }
}

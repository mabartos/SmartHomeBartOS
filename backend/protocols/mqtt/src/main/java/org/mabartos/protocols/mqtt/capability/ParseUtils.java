/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.protocols.mqtt.capability;

import org.mabartos.api.data.mqtt.ConvertableToModel;
import org.mabartos.api.protocol.mqtt.topics.CapabilityTopic;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

public class ParseUtils {

    @SuppressWarnings("unchecked")
    public static <Model extends CapabilityEntity, Data extends ConvertableToModel> void parse(AppServices services, CapabilityTopic capabilityTopic, Data data) {
        Model model = (Model) services.capabilities().findByID(capabilityTopic.getCapabilityID());
        if (data != null && model != null) {
            Model result = (Model) data.editModel(model);
            services.capabilities().updateByID(capabilityTopic.getCapabilityID(), result);
        }
    }
}

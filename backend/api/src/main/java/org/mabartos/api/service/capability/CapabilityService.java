/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability;

import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;
import org.mabartos.api.service.capability.triggers.TriggerService;

import java.util.Set;

public interface CapabilityService extends CRUDService<CapabilityModel, Long>, AbleSendMQTT {

    TriggerService triggers();

    CapabilityModel createFromJSON(String JSON);

    CapabilityModel updateFromJson(Long ID, String JSON);

    CapabilityModel fromDataToModel(CapabilityWholeData data);

    Set<CapabilityModel> fromDataToModel(Set<CapabilityWholeData> caps);
}

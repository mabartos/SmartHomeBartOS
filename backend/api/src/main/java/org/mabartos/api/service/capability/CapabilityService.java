package org.mabartos.api.service.capability;

import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;

import java.util.Set;

public interface CapabilityService extends CRUDService<CapabilityModel, Long>, AbleSendMQTT {

    public CapabilityModel createFromJSON(String JSON);

    CapabilityModel updateFromJson(Long ID, String JSON);

    Set<CapabilityModel> fromDataToModel(Set<CapabilityWholeData> caps);
}

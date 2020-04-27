package org.mabartos.api.service;

import org.mabartos.persistence.model.CapabilityModel;

public interface CapabilityService extends CRUDService<CapabilityModel, Long>, AbleSendMQTT {

    CapabilityModel updateFromJson(Long ID, String JSON);

}

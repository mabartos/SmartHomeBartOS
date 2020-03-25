package org.mabartos.api.model;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.MqttClientModel;

public interface MqttClientService extends CRUDService<MqttClientModel, Long> {

    MqttClientModel getByHome(HomeModel home);

    MqttClientModel getByHomeID(Long homeID);
}

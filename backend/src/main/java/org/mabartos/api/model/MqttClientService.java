package org.mabartos.api.model;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.MqttClientModel;
import org.mabartos.persistence.model.HomeModel;

public interface MqttClientService extends CRUDService<MqttClientModel> {

    MqttClientModel getByHome(HomeModel home);

    MqttClientModel getByHomeID(Long homeID);
}

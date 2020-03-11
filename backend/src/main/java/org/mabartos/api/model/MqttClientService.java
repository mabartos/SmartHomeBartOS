package org.mabartos.api.model;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.MqttClientModel;

public interface MqttClientService extends CRUDService<MqttClientModel> {

    MqttClientModel getByHome(HomeModel home);

    MqttClientModel getByHomeID(Long homeID);
}

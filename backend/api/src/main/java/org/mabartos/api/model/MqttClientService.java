package org.mabartos.api.model;

import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.mqtt.MqttClientModel;
import org.mabartos.api.service.CRUDService;

public interface MqttClientService extends CRUDService<MqttClientModel, Long> {

    MqttClientModel getByHome(HomeModel home);

    MqttClientModel getByHomeID(Long homeID);
}

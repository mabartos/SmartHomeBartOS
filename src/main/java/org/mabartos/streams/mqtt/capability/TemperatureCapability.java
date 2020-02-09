package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.TemperatureDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class TemperatureCapability extends GeneralMqttCapability<TemperatureDevModel> {

    public TemperatureCapability(){
        super();
    }

    public TemperatureCapability(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, CapabilityType.TEMPERATURE, id, message);
    }

}

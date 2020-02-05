package org.mabartos.streams.mqtt.devices;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.devices.TemperatureDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class TemperatureDevice extends GeneralMqttDevice<TemperatureDevModel> {

    public TemperatureDevice(){
        super();
    }

    public TemperatureDevice(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, CapabilityType.TEMPERATURE, id, message);
    }

}

package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.LightDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class LightCapability extends GeneralMqttCapability<LightDevModel> {

    public LightCapability(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, CapabilityType.LIGHT, id, message);
    }
}

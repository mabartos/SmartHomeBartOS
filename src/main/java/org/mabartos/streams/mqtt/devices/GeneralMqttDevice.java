package org.mabartos.streams.mqtt.devices;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class GeneralMqttDevice<Model> {

    protected DeviceService deviceService;
    protected MqttMessage message;
    protected Model model;
    protected CapabilityType type;
    protected BarMqttClient client;

    public GeneralMqttDevice(){}

    public GeneralMqttDevice(BarMqttClient client, DeviceService deviceService, CapabilityType type, Long id, MqttMessage message) {
        this.deviceService = deviceService;
        this.message = message;
        this.type = type;
        this.client = client;
        DeviceModel found = deviceService.findByID(id);
        /*if (found != null && found.getType().equals(type)) {
            this.model = (Model) found;
        }

         */
    }

    public void parseMessage() {
        System.out.println("Device type: " + type + ", message: " + message);
    }
}

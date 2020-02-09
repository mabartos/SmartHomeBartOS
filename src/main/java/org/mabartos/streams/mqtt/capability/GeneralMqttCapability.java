package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

import java.util.logging.Logger;

public class GeneralMqttCapability<Model> {

    protected static Logger logger = Logger.getLogger(GeneralMqttCapability.class.getName());

    protected DeviceService deviceService;
    protected MqttMessage message;
    protected Model model;
    protected CapabilityType type;
    protected BarMqttClient client;

    public GeneralMqttCapability() {
    }

    public GeneralMqttCapability(BarMqttClient client, DeviceService deviceService, CapabilityType type, Long id, MqttMessage message) {
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
        logger.info("Device type: " + type + ", message: " + message);
    }
}

package org.mabartos.streams.mqtt.devices;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.devices.HeaterDevModel;
import org.mabartos.persistence.model.devices.HumidityDevModel;
import org.mabartos.persistence.model.devices.TemperatureDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.service.core.HomeService;
import org.mabartos.streams.mqtt.BarMqttClient;
import org.mabartos.streams.mqtt.MqttTopics;
import org.mabartos.streams.mqtt.messages.MqttAddDeviceMessage;
import org.mabartos.streams.mqtt.messages.MqttGeneralMessage;

public class HandleManageMessage {

    private BarMqttClient client;
    private String topic;
    private MqttMessage message;
    private DeviceService deviceService;
    private HomeService homeService;
    private HomeModel home;

    public HandleManageMessage(HomeService homeService, DeviceService deviceService, HomeModel home, BarMqttClient client, String topic, MqttMessage message) {
        this.client = client;
        this.topic = topic;
        this.message = message;
        this.deviceService = deviceService;
        this.homeService = homeService;
        this.home = home;
    }

    public boolean handle() {
        if (topic.equals(MqttTopics.CONNECT_TOPIC)) {
            handleConnect();
            return true;
        } else if (topic.equals(MqttTopics.ADD_DEVICE_TOPIC)) {
            handleAdd();
            return true;
        } else if (topic.equals(MqttTopics.REMOVE_DEVICE_TOPIC)) {
            handleRemove();
            return true;
        }
        return false;
    }

    private void handleConnect() {
        if (servicesAreValid()) {

        }

    }

    private void handleAdd() {
        MqttAddDeviceMessage deviceMessage = MqttAddDeviceMessage.fromJson(message.toString());
        if (deviceMessage != null && servicesAreValid()) {
            DeviceModel device = deviceService.create(getTypedInstance(deviceMessage.getName(), deviceMessage.getType()));

            if (homeService.addDeviceToHome(device, home.getID())) {
                MqttGeneralMessage response = new MqttGeneralMessage(device);
                client.publish(this.topic, response.toJson());
            }
        }
    }

    private void handleRemove() {

    }

    private boolean servicesAreValid() {
        return deviceService != null && homeService != null;
    }

    private DeviceModel getTypedInstance(String name, DeviceType type) {
        if (name != null) {
            switch (type) {
                case NONE:
                    break;
                case TEMPERATURE:
                    return new TemperatureDevModel(name);
                case HUMIDITY:
                    return new HumidityDevModel(name);
                case HEATER:
                    return new HeaterDevModel(name);
                case LIGHT:
                    break;
                case RELAY:
                    break;
                case SOCKET:
                    break;
                case PIR:
                    break;
                case GAS_SENSOR:
                    break;
                case STATISTICS:
                    break;
                case AIRCONDITIONER:
                    break;
                case OTHER:
                    break;
                default:
            }
        }
        return null;
    }
}

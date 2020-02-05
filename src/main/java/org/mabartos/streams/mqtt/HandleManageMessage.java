package org.mabartos.streams.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.devices.HeaterDevModel;
import org.mabartos.persistence.model.devices.HumidityDevModel;
import org.mabartos.persistence.model.devices.TemperatureDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.service.core.HomeService;
import org.mabartos.streams.mqtt.exceptions.DeviceConflictException;
import org.mabartos.streams.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.streams.mqtt.messages.BarMqttSender;
import org.mabartos.streams.mqtt.messages.CapabilityJSON;
import org.mabartos.streams.mqtt.messages.MqttAddDeviceMessage;
import org.mabartos.streams.mqtt.messages.MqttGeneralMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.sql.SQLOutput;

@ApplicationScoped
public class HandleManageMessage {

    private BarMqttClient client;
    private String topic;
    private MqttMessage message;
    private HomeModel home;

    @Inject
    private DeviceService deviceService;
    @Inject
    private HomeService homeService;

    public void onStartup(@Observes StartupEvent start){
        System.out.println("Handle Manage Message created");
    }

    @Inject
    public HandleManageMessage() {
    }

    public HandleManageMessage(HomeModel home, BarMqttClient client, String topic, MqttMessage message) {
        this.client = client;
        this.topic = topic;
        this.message = message;
        //this.deviceService = deviceService;
        //this.homeService = homeService;
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
        String receivedTopic = home.getTopic();
        try {
            MqttAddDeviceMessage deviceMessage = MqttAddDeviceMessage.fromJson(message.toString());
            System.out.println(deviceMessage.toJson());
            if (servicesAreValid()) {
                DeviceModel deviceModel = new DeviceModel(deviceMessage.getName(), CapabilityJSON.toModel(deviceMessage.getCapabilities()));
                DeviceModel device = deviceService.create(deviceModel);

                if (homeService.addDeviceToHome(device, home.getID())) {
                    MqttGeneralMessage response = new MqttGeneralMessage(device);
                    client.publish(receivedTopic, response.toJson());
                }
            }


        } catch (DeviceConflictException e) {
            BarMqttSender.sendAddDeviceResponse(client, receivedTopic, HttpResponseStatus.CONFLICT, e.getMessage());
        } catch (WrongMessageTopicException wm) {
            BarMqttSender.sendAddDeviceResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        }
    }

    private void handleRemove() {

    }

    private boolean servicesAreValid() {
        return deviceService != null && homeService != null;
    }

    private DeviceModel getTypedInstance(String name, CapabilityType type) {
        if (name != null && type != null) {
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

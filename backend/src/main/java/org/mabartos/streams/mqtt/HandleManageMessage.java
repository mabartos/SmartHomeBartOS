package org.mabartos.streams.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.capability.HeaterCapModel;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.service.core.CapabilityService;
import org.mabartos.service.core.DeviceService;
import org.mabartos.service.core.HomeService;
import org.mabartos.streams.mqtt.exceptions.DeviceConflictException;
import org.mabartos.streams.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.streams.mqtt.messages.BarMqttSender;
import org.mabartos.streams.mqtt.messages.CapabilityJSON;
import org.mabartos.streams.mqtt.messages.MqttAddDeviceMessage;
import org.mabartos.streams.mqtt.messages.MqttGeneralMessage;
import org.mabartos.streams.mqtt.topics.CRUDTopic;
import org.mabartos.streams.mqtt.topics.GeneralTopic;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class HandleManageMessage {

    public static Logger logger = Logger.getLogger(HandleManageMessage.class.getName());

    private BarMqttClient client;
    private GeneralTopic topic;
    private MqttMessage message;
    private HomeModel home;

    DeviceService deviceService;
    HomeService homeService;
    CapabilityService capabilityService;

    public void onStartup(@Observes StartupEvent start) {
        logger.info("Initialized Handle Manage Message Bean");
    }

    @Inject
    public HandleManageMessage(DeviceService deviceService, HomeService homeService, CapabilityService capabilityService) {
        this.deviceService = deviceService;
        this.homeService = homeService;
        this.capabilityService = capabilityService;
    }

    public void init(HomeModel home, BarMqttClient client, GeneralTopic topic, MqttMessage message) {
        this.client = client;
        this.topic = topic;
        this.message = message;
        this.home = home;
    }

    public boolean handleManageTopics() {
        if (topic != null) {
            if (topic instanceof CRUDTopic) {
                CRUDTopic crudTopic = (CRUDTopic) topic;
                switch (crudTopic.getTypeCRUD()) {
                    case CONNECT:
                        return handleConnect();
                    case CREATE:
                        return handleCreate();
                    case REMOVE:
                        return handleRemove();
                    case UPDATE:
                        return handleUpdate();
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    private boolean handleConnect() {
        if (servicesAreValid()) {
        }
        return false;
    }

    private boolean handleCreate() {
        String receivedTopic = home.getTopic();
        try {
            MqttAddDeviceMessage deviceMessage = MqttAddDeviceMessage.fromJson(message.toString());
            if (servicesAreValid() && receivedTopic != null && deviceMessage != null) {
                DeviceModel device = createDeviceFromMessage(deviceMessage);

                if (homeService.addDeviceToHome(device, home.getID())) {
                    MqttGeneralMessage response = new MqttGeneralMessage(device, deviceMessage.getIdMessage());
                    client.publish(receivedTopic, response.toJson());
                    return true;
                }
            }
        } catch (DeviceConflictException e) {
            BarMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.CONFLICT, e.getMessage());
        } catch (WrongMessageTopicException wm) {
            BarMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        }
        return false;
    }

    //TODO
    private boolean handleRemove() {
        return false;
    }

    //TODO
    private boolean handleUpdate() {
        return false;
    }

    private boolean servicesAreValid() {
        return deviceService != null && homeService != null && capabilityService != null;
    }

    private CapabilityModel getTypedInstance(String name, CapabilityType type) {
        if (name != null && type != null) {
            switch (type) {
                case NONE:
                    break;
                case TEMPERATURE:
                    return new TemperatureCapModel(name);
                case HUMIDITY:
                    return new HumidityCapModel(name);
                case HEATER:
                    return new HeaterCapModel(name);
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

    private DeviceModel createDeviceFromMessage(MqttAddDeviceMessage message) {
        List<CapabilityModel> capabilities = CapabilityJSON.toModel(message.getCapabilities())
                .stream()
                .map(f -> getTypedInstance(f.getName(), f.getType()))
                .collect(Collectors.toList());
        List<CapabilityModel> result = new ArrayList<>();
        capabilities.forEach(f -> result.add(capabilityService.create(f)));
        DeviceModel deviceModel = new DeviceModel(message.getName(), result);
        return deviceService.create(deviceModel);
    }
}

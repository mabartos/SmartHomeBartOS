package org.mabartos.protocols.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.capability.HeaterCapModel;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.protocols.mqtt.exceptions.DeviceConflictException;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.protocols.mqtt.messages.BarMqttSender;
import org.mabartos.protocols.mqtt.messages.CapabilityJSON;
import org.mabartos.protocols.mqtt.messages.MqttAddDeviceMessage;
import org.mabartos.protocols.mqtt.messages.MqttGeneralMessage;
import org.mabartos.protocols.mqtt.topics.CRUDTopic;
import org.mabartos.protocols.mqtt.topics.GeneralTopic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class HandleManageMessage implements Serializable {

    public static Logger logger = Logger.getLogger(HandleManageMessage.class.getName());

    private GeneralTopic topic;
    private MqttMessage message;
    private HomeModel home;
    private BartMqttClient client;
    AppServices services;

    @Inject
    public HandleManageMessage(AppServices services) {
        this.services=services;
    }

    public void init(BartMqttClient client, HomeModel home, GeneralTopic topic, MqttMessage message) {
        this.topic = topic;
        this.message = message;
        this.home = home;
        this.client = client;
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
        if (services != null) {
        }
        return false;
    }

    private boolean handleCreate() {
        String receivedTopic = home.getMqttClient().getTopic();
        try {
            MqttAddDeviceMessage deviceMessage = MqttAddDeviceMessage.fromJson(message.toString());
            if (services != null && receivedTopic != null && deviceMessage != null) {
                DeviceModel device = createDeviceFromMessage(deviceMessage);

                if (services.homes().addDeviceToHome(device, home.getID())) {
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
        Set<CapabilityModel> capabilities = CapabilityJSON.toModel(message.getCapabilities())
                .stream()
                .map(f -> getTypedInstance(f.getName(), f.getType()))
                .collect(Collectors.toSet());
        Set<CapabilityModel> result = new HashSet<>();
        capabilities.forEach(f -> result.add(services.capabilities().create(f)));
        DeviceModel deviceModel = new DeviceModel(message.getName(), result);
        return services.devices().create(deviceModel);
    }
}

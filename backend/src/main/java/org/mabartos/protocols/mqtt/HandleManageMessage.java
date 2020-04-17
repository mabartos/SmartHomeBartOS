package org.mabartos.protocols.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.home.HomeModel;
import org.mabartos.persistence.model.capability.HeaterCapModel;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.protocols.mqtt.data.AddDeviceRequestData;
import org.mabartos.protocols.mqtt.data.BartMqttSender;
import org.mabartos.protocols.mqtt.data.CapabilityData;
import org.mabartos.protocols.mqtt.data.DeviceData;
import org.mabartos.protocols.mqtt.exceptions.DeviceConflictException;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
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
    private CRUDTopic crudTopic;
    private String receivedTopic;

    private MqttMessage message;
    private HomeModel home;
    private BartMqttClient client;
    AppServices services;

    @Inject
    public HandleManageMessage(AppServices services) {
        this.services = services;
    }

    public void init(BartMqttClient client, HomeModel home, GeneralTopic topic, MqttMessage message) {
        this.topic = topic;
        this.message = message;
        this.home = home;
        this.client = client;
        this.receivedTopic = home.getMqttClient().getTopic();
    }

    public boolean handleManageTopics() {
        if (topic instanceof CRUDTopic && services != null) {
            crudTopic = (CRUDTopic) topic;
            this.home = services.homes().findByID(this.home.getID());
            switch (crudTopic.getTypeCRUD()) {
                case CONNECT:
                    return handleConnect();
                case CREATE:
                    return handleCreate();
                case REMOVE_FROM_HOME:
                    return handleRemoveFromHome();
                case UPDATE:
                    return handleUpdate();
                case DELETE:
                    return handleDelete();
                default:
                    return false;
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
        try {
            AddDeviceRequestData deviceMessage = AddDeviceRequestData.fromJson(message.toString());
            if (receivedTopic != null && deviceMessage != null) {
                DeviceModel device = createDeviceFromMessage(deviceMessage);
                if (device == null)
                    throw new WrongMessageTopicException();

                if (services.homes().addDeviceToHome(device, home.getID())) {
                    DeviceData response = new DeviceData(deviceMessage.getMsgID(), device);
                    client.publish(receivedTopic, response.toJson());
                    return true;
                }
            }
        } catch (DeviceConflictException e) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.CONFLICT, e.getMessage());
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        }
        return false;
    }

    private boolean handleRemoveFromHome() {
        try {
            if (isContainedInHome(crudTopic)) {
                DeviceModel device = services.devices().findByID(crudTopic.getDeviceID());
                services.homes().removeDeviceFromHome(device, home.getID());
                BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.OK);
                return true;
            }
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        } catch (Exception e) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean handleUpdate() {
        try {
            DeviceData deviceData = DeviceData.fromJson(message.toString());
            if (isContainedInHome(crudTopic) && deviceData != null) {
                DeviceModel device = deviceData.toModel();
                services.devices().updateByID(device.getID(), device);
                BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.OK, "Device was updated");
                return true;
            }
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        } catch (Exception e) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST);
        }
        return false;
    }

    private boolean handleDelete() {
        try {
            if (isContainedInHome(crudTopic) && services.devices().deleteByID(crudTopic.getDeviceID())) {
                BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.OK, "Device was deleted");
                return true;
            }
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        } catch (Exception e) {
            BartMqttSender.sendResponse(client, receivedTopic, HttpResponseStatus.BAD_REQUEST);
        }
        return false;

    }


    //TODO
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
                    return new LightCapModel(name);
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
                case AIR_CONDITIONER:
                    break;
                case OTHER:
                    break;
                default:
            }
        }
        return null;
    }

    private boolean isContainedInHome(CRUDTopic crudTopic) {
        boolean isContained = home.getUnAssignedDevices().stream().anyMatch(f -> f.getID().equals(crudTopic.getDeviceID()));
        if (!isContained)
            throw new WrongMessageTopicException("Device doesn't belong to home");
        return true;
    }

    private DeviceModel createDeviceFromMessage(AddDeviceRequestData message) {
        try {
            Set<CapabilityModel> capabilities = CapabilityData.toModel(message.getCapabilities())
                    .stream()
                    .map(f -> getTypedInstance(f.getName(), f.getType()))
                    .collect(Collectors.toSet());

            Set<CapabilityModel> result = new HashSet<>();

            if (services != null && services.capabilities() != null) {
                DeviceModel device = services.devices().create(new DeviceModel(message.getName(),capabilities));
                return device;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}

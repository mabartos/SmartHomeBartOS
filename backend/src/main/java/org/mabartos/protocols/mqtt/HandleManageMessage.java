package org.mabartos.protocols.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.capability.HeaterCapModel;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.persistence.model.home.HomeModel;
import org.mabartos.protocols.mqtt.data.capability.CapabilityData;
import org.mabartos.protocols.mqtt.data.device.AddDeviceRequestData;
import org.mabartos.protocols.mqtt.data.device.AddDeviceToRoomData;
import org.mabartos.protocols.mqtt.data.device.ConnectRequestData;
import org.mabartos.protocols.mqtt.data.device.ConnectResponseData;
import org.mabartos.protocols.mqtt.data.device.DeviceData;
import org.mabartos.protocols.mqtt.data.general.BartMqttSender;
import org.mabartos.protocols.mqtt.exceptions.DeviceConflictException;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.protocols.mqtt.topics.CRUDTopic;
import org.mabartos.protocols.mqtt.topics.GeneralTopic;
import org.mabartos.protocols.mqtt.utils.TopicUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class HandleManageMessage implements Serializable {

    public static Logger logger = Logger.getLogger(HandleManageMessage.class.getName());

    private GeneralTopic topic;
    private CRUDTopic crudTopic;
    private String rawTopic;

    private MqttMessage message;
    private HomeModel home;
    private BartMqttClient client;
    AppServices services;

    @Inject
    public HandleManageMessage(AppServices services) {
        this.services = services;
    }

    public void init(BartMqttClient client, HomeModel home, GeneralTopic topic, MqttMessage message, String rawTopic) {
        this.topic = topic;
        this.message = message;
        this.home = home;
        this.client = client;
        this.rawTopic = rawTopic;
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
                case GET_ROOM:
                    return handleGetRoom();
                default:
                    return false;
            }
        }
        return false;
    }

    private boolean handleConnect() {
        try {
            System.out.println("CONNECT");
            ConnectRequestData connect = ConnectRequestData.fromJson(message.toString());
            if (connect == null)
                throw new WrongMessageTopicException();

            DeviceModel device = services.devices().findByID(connect.getID());
            if (device == null)
                throw new WrongMessageTopicException();

            device.setActive(true);
            device = services.devices().updateByID(device.getID(), device);
            if (device != null) {
                ConnectResponseData response = new ConnectResponseData(connect.getMsgID(), device);
                client.publish(TopicUtils.getConnectTopicResp(home.getID()), response.toJson());
                return true;
            }
        } catch (RuntimeException e) {
            BartMqttSender.sendResponse(client, TopicUtils.getConnectTopicResp(home.getID()), HttpResponseStatus.BAD_REQUEST, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean handleCreate() {
        try {
            AddDeviceRequestData deviceMessage = AddDeviceRequestData.fromJson(message.toString());
            if (rawTopic != null && deviceMessage != null) {
                DeviceModel device = createDeviceFromMessage(deviceMessage);
                if (device == null)
                    throw new WrongMessageTopicException();
                device.setActive(true);

                if (services.homes().addDeviceToHome(device, home.getID())) {
                    DeviceData response = new DeviceData(deviceMessage.getMsgID(), device, true);
                    client.publish(TopicUtils.getCreateTopicResp(home.getID()), response.toJson(),0);
                    return true;
                }
            } else
                throw new WrongMessageTopicException();
        } catch (DeviceConflictException e) {
            BartMqttSender.sendResponse(client, TopicUtils.getCreateTopicResp(home.getID()), HttpResponseStatus.CONFLICT, e.getMessage());
        } catch (RuntimeException e) {
            BartMqttSender.sendResponse(client, TopicUtils.getCreateTopicResp(home.getID()), HttpResponseStatus.BAD_REQUEST, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean handleRemoveFromHome() {
        try {
            if (isContainedInHome(crudTopic)) {
                DeviceModel device = services.devices().findByID(crudTopic.getDeviceID());
                services.homes().removeDeviceFromHome(device, home.getID());
                BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.OK);
                return true;
            }
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        } catch (RuntimeException e) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST, e.getMessage());
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
                BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.OK, "Device was updated");
                return true;
            }
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        } catch (RuntimeException e) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST);
        }
        return false;
    }

    private boolean handleGetRoom() {
        try {
            AddDeviceToRoomData data = AddDeviceToRoomData.fromJson(message.toString());
            if (isContainedInHome(crudTopic) && data != null && !data.isResponse()) {
                DeviceModel device = services.devices().findByID(data.getDeviceID());
                if (device != null && device.getRoom() != null) {
                    client.publish(TopicUtils.getDeviceTopic(home.getID(), device.getID()), new AddDeviceToRoomData(device.getRoomID(), device.getID(), true).toJson());
                    return true;
                }
            }
        } catch (RuntimeException e) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST);
        }
        return false;
    }

    private boolean handleDelete() {
        try {
            if (isContainedInHome(crudTopic) && services.devices().deleteByID(crudTopic.getDeviceID())) {
                BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.OK, "Device was deleted");
                return true;
            }
        } catch (WrongMessageTopicException wm) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST, wm.getMessage());
        } catch (RuntimeException e) {
            BartMqttSender.sendResponse(client, rawTopic, HttpResponseStatus.BAD_REQUEST);
        }
        return false;
    }

    //TODO
    private CapabilityModel getTypedInstance(String name, CapabilityType type, Integer pin) {
        if (name != null && type != null) {
            switch (type) {
                case NONE:
                    break;
                case TEMPERATURE:
                    return new TemperatureCapModel(name, pin);
                case HUMIDITY:
                    return new HumidityCapModel(name, pin);
                case HEATER:
                    return new HeaterCapModel(name, pin);
                case LIGHT:
                    return new LightCapModel(name, pin);
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
                    .map(f -> getTypedInstance(f.getName(), f.getType(), f.getPin()))
                    .collect(Collectors.toSet());

            if (services != null && services.capabilities() != null) {
                return services.devices().create(new DeviceModel(message.getName(), capabilities));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}

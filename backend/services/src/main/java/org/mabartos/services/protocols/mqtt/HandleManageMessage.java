/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.protocols.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.data.general.device.AddDeviceRequestData;
import org.mabartos.api.data.general.device.AddDeviceToRoomData;
import org.mabartos.api.data.general.device.ConnectRequestData;
import org.mabartos.api.data.general.device.ConnectResponseData;
import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.data.mqtt.BartMqttSender;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.protocol.mqtt.exceptions.DeviceConflictException;
import org.mabartos.api.protocol.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.api.protocol.mqtt.topics.CRUDTopic;
import org.mabartos.api.protocol.mqtt.topics.GeneralTopic;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityUtils;
import org.mabartos.persistence.jpa.model.services.device.DeviceEntity;

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
                case LOGOUT:
                    return handleLogout();
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
                    client.publish(TopicUtils.getCreateTopicResp(home.getID()), response.toJson(), 0, false);
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
                DeviceModel device = services.devices().fromDataToModel(deviceData);
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

    private boolean handleLogout() {
        try {
            DeviceModel device = services.devices().findByID(crudTopic.getDeviceID());
            if (device == null)
                throw new WrongMessageTopicException();

            device.setActive(false);
            return services.devices().updateByID(device.getID(), device) != null;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isContainedInHome(CRUDTopic crudTopic) {
        boolean isContained = home.getUnAssignedDevices().stream().anyMatch(f -> f.getID().equals(crudTopic.getDeviceID()));
        if (!isContained)
            throw new WrongMessageTopicException("Device doesn't belong to home");
        return true;
    }

    private DeviceModel createDeviceFromMessage(AddDeviceRequestData message) {
        try {
            Set<CapabilityModel> capabilities = services.capabilities().fromDataToModel(message.getCapabilities())
                    .stream()
                    .map(CapabilityUtils::getEntityInstance)
                    .collect(Collectors.toSet());

            if (services != null && services.capabilities() != null) {
                return services.devices().create(new DeviceEntity(message.getName(), capabilities));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}

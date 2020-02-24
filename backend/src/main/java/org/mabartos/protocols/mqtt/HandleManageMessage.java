package org.mabartos.protocols.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.model.BartSession;
import org.mabartos.api.protocol.BartMqttClient;
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
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class HandleManageMessage {

    public static Logger logger = Logger.getLogger(HandleManageMessage.class.getName());

    private BartMqttClient client;
    private GeneralTopic topic;
    private MqttMessage message;
    private HomeModel home;

    BartSession session;

    public void onStartup(@Observes StartupEvent start) {
        logger.info("Initialized Handle Manage Message Bean");
    }

    @Inject
    public HandleManageMessage(BartSession session) {
        this.session = session;

    }

    public void init(HomeModel home, GeneralTopic topic, MqttMessage message) {
        this.client = session.getMqttClient();
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

                if (session.homes().addDeviceToHome(device, home.getID())) {
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
        return session.devices() != null && session.homes() != null && session.capabilities() != null;
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
        capabilities.forEach(f -> result.add(session.capabilities().create(f)));
        DeviceModel deviceModel = new DeviceModel(message.getName(), result);
        return session.devices().create(deviceModel);
    }
}

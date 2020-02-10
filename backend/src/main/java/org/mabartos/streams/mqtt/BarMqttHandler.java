package org.mabartos.streams.mqtt;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.logmanager.Level;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.service.core.CapabilityService;
import org.mabartos.service.core.DeviceService;
import org.mabartos.service.core.HomeService;
import org.mabartos.streams.mqtt.capability.HumidityCapability;
import org.mabartos.streams.mqtt.capability.LightCapability;
import org.mabartos.streams.mqtt.capability.TemperatureCapability;
import org.mabartos.streams.mqtt.topics.CapabilityTopic;
import org.mabartos.streams.mqtt.topics.GeneralTopic;
import org.mabartos.streams.mqtt.utils.TopicUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class BarMqttHandler {
    public static Logger logger = Logger.getLogger(BarMqttHandler.class.getName());

    DeviceService deviceService;
    HomeService homeService;
    CapabilityService capabilityService;
    HandleManageMessage handler;

    void onStartup(@Observes StartupEvent event) {
        logger.info("Initialized BarMqttHandler Bean");
    }

    @Inject
    public BarMqttHandler(DeviceService deviceService, HomeService homeService, CapabilityService capabilityService, HandleManageMessage handler) {
        this.deviceService = deviceService;
        this.homeService = homeService;
        this.capabilityService = capabilityService;
        this.handler = handler;
    }

    //TODO own exceptions, common topics
    public void executeMessage(HomeModel home, BarMqttClient client, final String receivedTopic, final MqttMessage message) {
        String homeTopic = TopicUtils.getHomeTopic(home);
        try {
            GeneralTopic resultTopic = TopicUtils.getSpecificTopic(receivedTopic);

            if (resultTopic != null && homeTopic != null && receivedTopic.length() > homeTopic.length()) {
                handler.init(home, client, resultTopic, message);

                // It's the 'manage' topic
                if (handler.handleManageTopics())
                    return;

                if (resultTopic instanceof CapabilityTopic) {
                    CapabilityTopic capTopic = (CapabilityTopic) resultTopic;
                    redirectParsing(client, capTopic, message);
                }
            }
        } catch (IndexOutOfBoundsException iobe) {
            logger.log(Level.ERROR, "Invalid topic : " + receivedTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectParsing(BarMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        switch (capabilityTopic.getCapabilityType()) {
            case NONE:
                break;
            case TEMPERATURE:
                new TemperatureCapability(client, capabilityService, capabilityTopic, message).parseMessage();
                break;
            case HUMIDITY:
                new HumidityCapability(client, capabilityService, capabilityTopic, message).parseMessage();
                break;
            case HEATER:
                break;
            case LIGHT:
                new LightCapability(client, capabilityService, capabilityTopic, message).parseMessage();
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
                // NOP
                break;
        }
    }

}

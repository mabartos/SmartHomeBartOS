package mqtt;

import io.quarkus.runtime.StartupEvent;
import model.BartSession;
import models.HomeModel;
import mqtt.capability.HumidityCapability;
import mqtt.capability.LightCapability;
import mqtt.capability.TemperatureCapability;
import mqtt.topics.CapabilityTopic;
import mqtt.topics.GeneralTopic;
import mqtt.utils.TopicUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.logmanager.Level;
import protocol.BartMqttClient;
import service.CapabilityService;
import service.DeviceService;
import service.HomeService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import java.util.logging.Logger;

@ApplicationScoped
public class BartMqttHandler {
    public static Logger logger = Logger.getLogger(BartMqttHandler.class.getName());

    DeviceService deviceService;
    HomeService homeService;
    CapabilityService capabilityService;
    HandleManageMessage handler;

    @Context
    BartSession session;

    void onStartup(@Observes StartupEvent event) {
        logger.info("Initialized BarMqttHandler Bean");
    }

    @Inject
    public BartMqttHandler(HandleManageMessage handler) {
        this.deviceService = session.devices();
        this.homeService = session.homes();
        this.capabilityService = session.capabilities();
        this.handler = handler;
    }

    //TODO own exceptions, common topics
    public void executeMessage(HomeModel home, BartMqttClient client, final String receivedTopic, final MqttMessage message) {
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

    private void redirectParsing(BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
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

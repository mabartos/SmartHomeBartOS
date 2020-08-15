/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.protocols.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.logmanager.Level;
import org.mabartos.api.data.mqtt.BartMqttSender;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.BartMqttHandler;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.protocol.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.api.protocol.mqtt.topics.CapabilityTopic;
import org.mabartos.api.protocol.mqtt.topics.GeneralTopic;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.capability.extern.ExternBtnCapability;
import org.mabartos.protocols.mqtt.capability.humidity.HumidityCapability;
import org.mabartos.protocols.mqtt.capability.light.LightCapability;
import org.mabartos.protocols.mqtt.capability.pir.PIRCapability;
import org.mabartos.protocols.mqtt.capability.relay.RelayCapability;
import org.mabartos.protocols.mqtt.capability.temperature.TemperatureCapability;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultBartMqttHandler implements BartMqttHandler {
    public static Logger logger = Logger.getLogger(DefaultBartMqttHandler.class.getName());

    BartMqttClient mqttClient;
    AppServices services;

    @Inject
    public DefaultBartMqttHandler(AppServices services) {
        this.services = services;
    }

    @Override
    public void executeMessage(BartMqttClient client, HomeModel home, final String rawTopic, final MqttMessage message) {
        if (home == null || rawTopic == null || message == null)
            return;

        this.mqttClient = client;

        try {
            GeneralTopic resultTopic = TopicUtils.getSpecificTopic(rawTopic);
            if (resultTopic instanceof CapabilityTopic) {
                CapabilityTopic capTopic = (CapabilityTopic) resultTopic;
                redirectParsing(capTopic, message);
            }
        } catch (IndexOutOfBoundsException iobe) {
            logger.log(Level.ERROR, "Invalid topic : " + rawTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectParsing(CapabilityTopic capabilityTopic, MqttMessage message) {
        try {
            switch (capabilityTopic.getCapabilityType()) {
                case NONE:
                    break;
                case TEMPERATURE:
                    new TemperatureCapability(services, mqttClient, capabilityTopic, message).parseMessage();
                    break;
                case HUMIDITY:
                    new HumidityCapability(services, mqttClient, capabilityTopic, message).parseMessage();
                    break;
                case EXTERN_BTN:
                    new ExternBtnCapability(services, mqttClient, capabilityTopic, message).parseMessage();
                    break;
                case LIGHT:
                    new LightCapability(services, mqttClient, capabilityTopic, message).parseMessage();
                    break;
                case RELAY:
                    new RelayCapability(services, mqttClient, capabilityTopic, message).parseMessage();
                    break;
                case SOCKET:
                    break;
                case PIR:
                    new PIRCapability(services, mqttClient, capabilityTopic, message).parseMessage();
                case GAS_SENSOR:
                    break;
                case STATISTICS:
                    break;
                case AIR_CONDITIONER:
                    break;
                case OTHER:
                    break;
                default:
                    // NOP
                    break;
            }
        } catch (WrongMessageTopicException wme) {
            BartMqttSender.sendResponse(mqttClient, 400, "Wrong message");
        }
    }

}

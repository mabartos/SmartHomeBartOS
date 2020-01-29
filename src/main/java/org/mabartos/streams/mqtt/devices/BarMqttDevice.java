package org.mabartos.streams.mqtt.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.MqttTopics;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

@ApplicationScoped
public class BarMqttDevice {

    @Inject
    private DeviceService deviceService;

    //TODO own exceptions, common topics
    public void executeMessage(String topic, String message) {
        try {
            if (topic.startsWith(MqttTopics.BASIC_TOPIC) && topic.length() > MqttTopics.BASIC_TOPIC.length()) {
                String specificTopic = topic.substring(MqttTopics.BASIC_TOPIC.length());

                if (specificTopic.equals(MqttTopics.CONNECT_TOPIC)) {
                    return;
                } else if (specificTopic.equals(MqttTopics.ADD_DEVICE_TOPIC)) {
                    return;
                } else if (specificTopic.equals(MqttTopics.REMOVE_DEVICE_TOPIC)) {
                    return;
                }

                Optional<DeviceType> optionalType = Arrays.stream(DeviceType.values())
                        .filter(f -> f.getTopic().equals(specificTopic))
                        .findFirst();

                if (!optionalType.isPresent())
                    return;

                DeviceType type = optionalType.get();
                Long idDevice = Long.parseLong(specificTopic.substring(type.getTopic().length()));

                redirectParsing(type, idDevice, message);
            }
        } catch (IndexOutOfBoundsException iobe) {
            System.err.println("Invalid topic : " + topic);
            iobe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void redirectParsing(DeviceType type, Long idDevice, String message) {
        switch (type) {
            case NONE:
                break;
            case TEMPERATURE:
                new TemperatureDevice(deviceService, idDevice, message).parseMessage();
                break;
            case HUMIDITY:
                new HumidityDevice(deviceService, idDevice, message).parseMessage();
                break;
            case HEATER:
                break;
            case LIGHT:
                new LightDevice(deviceService, idDevice, message).parseMessage();
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

package org.mabartos.streams.mqtt;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.service.core.HomeService;
import org.mabartos.streams.mqtt.devices.HumidityDevice;
import org.mabartos.streams.mqtt.devices.LightDevice;
import org.mabartos.streams.mqtt.devices.TemperatureDevice;
import org.mabartos.streams.mqtt.utils.TopicUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

@ApplicationScoped
public class BarMqttHandler {

    DeviceService deviceService;
    HomeService homeService;

    void onStartup(@Observes StartupEvent event){
        System.out.println("Start Handler");
    }

    @Inject
    public BarMqttHandler(DeviceService deviceService, HomeService homeService) {
        this.deviceService = deviceService;
        this.homeService = homeService;
    }

    //TODO own exceptions, common topics
    public void executeMessage(HomeModel home, BarMqttClient client, String receivedTopic, MqttMessage message) {
        try {
            String homeTopic = TopicUtils.getTopic(home);

            if (homeTopic.equals(receivedTopic.substring(0, homeTopic.length())) && receivedTopic.length() > homeTopic.length()) {

                String specificTopic = receivedTopic.substring(homeTopic.length());

                HandleManageMessage handler = new HandleManageMessage(homeService, deviceService, home, client, specificTopic, message);

                // It's not the 'manage' topic
                if (handler.handle())
                    return;

                Optional<DeviceType> optionalType = Arrays.stream(DeviceType.values())
                        .filter(f -> f.getTopic().equals(specificTopic))
                        .findFirst();

                if (!optionalType.isPresent())
                    return;

                DeviceType type = optionalType.get();
                Long idDevice = Long.parseLong(specificTopic.substring(type.getTopic().length()));

                redirectParsing(client, type, idDevice, message);
            }
        } catch (IndexOutOfBoundsException iobe) {
            System.err.println("Invalid topic : " + receivedTopic);
            iobe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectParsing(BarMqttClient client, DeviceType type, Long idDevice, MqttMessage message) {
        switch (type) {
            case NONE:
                break;
            case TEMPERATURE:
                new TemperatureDevice(client, deviceService, idDevice, message).parseMessage();
                break;
            case HUMIDITY:
                new HumidityDevice(client, deviceService, idDevice, message).parseMessage();
                break;
            case HEATER:
                break;
            case LIGHT:
                new LightDevice(client, deviceService, idDevice, message).parseMessage();
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

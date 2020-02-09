package org.mabartos.streams.mqtt.messages.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.streams.mqtt.messages.MqttGeneralMessage;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class TemperatureCapMessage extends MqttGeneralMessage {

    @JsonProperty("actualTemperature")
    private Double actualTemperature;

    @JsonCreator
    public TemperatureCapMessage(Long idMessage, Long id, String name, String topic,
                                 @JsonProperty("actualTemperature") Double actualTemperature) {
        super(idMessage, id, name, topic);
    }

    public Double getActualTemperature() {
        return actualTemperature;
    }

    public static TemperatureCapMessage fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, TemperatureCapMessage.class);
    }
}

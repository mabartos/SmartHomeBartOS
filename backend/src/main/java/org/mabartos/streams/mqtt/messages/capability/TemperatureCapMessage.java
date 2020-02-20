package org.mabartos.streams.mqtt.messages.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.streams.mqtt.messages.CapabilityJSON;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class TemperatureCapMessage extends CapabilityJSON {

    @JsonProperty("actual")
    private Double actualTemperature;

    @JsonCreator
    public TemperatureCapMessage(@JsonProperty("name") String name,
                                 @JsonProperty("type") CapabilityType type,
                                 @JsonProperty("actual") Double actualTemperature) {
        super(name, type);
        this.actualTemperature = actualTemperature;
    }

    public Double getActualTemperature() {
        return actualTemperature;
    }

    public static TemperatureCapMessage fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, TemperatureCapMessage.class);
    }
}

package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.protocols.mqtt.data.CapabilityData;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public class TemperatureData extends CapabilityData {

    @JsonProperty("actual")
    private Double actualTemperature;

    @JsonCreator
    public TemperatureData(@JsonProperty("name") String name,
                           @JsonProperty("type") CapabilityType type,
                           @JsonProperty("actual") Double actualTemperature) {
        super(name, type);
        this.actualTemperature = actualTemperature;
    }

    public Double getActualTemperature() {
        return actualTemperature;
    }

    public static TemperatureData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, TemperatureData.class);
    }
}

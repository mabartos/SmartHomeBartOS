package mqtt.messages.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.capability.utils.CapabilityType;
import mqtt.messages.CapabilityJSON;
import mqtt.utils.MqttSerializeUtils;


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

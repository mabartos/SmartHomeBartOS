package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public class HumidityData extends CapabilityData {

    @JsonProperty("actual")
    private Byte actual;

    public HumidityData(@JsonProperty("type") CapabilityType type,
                        @JsonProperty("pin") Integer pin,
                        @JsonProperty("actual") Byte actual) {
        super(type, pin);
        this.actual = actual;
    }

    public Byte getActual() {
        return actual;
    }

    public static HumidityData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, HumidityData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof HumidityCapModel) {
            super.editModel(model);
            HumidityCapModel hum = (HumidityCapModel) model;
            hum.setType(CapabilityType.HUMIDITY);
            hum.setValue(getActual());
            return hum;
        }
        return null;
    }
}

package org.mabartos.protocols.mqtt.data.capability.humidity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.humidity.HumidityCapModel;
import org.mabartos.protocols.mqtt.data.capability.CapabilityDataWithValue;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public class HumidityData extends CapabilityDataWithValue<Byte> {

    public HumidityData(@JsonProperty("actual") Byte actual) {
        super(actual);
    }

    @Override
    public Byte getValue() {
        return actual;
    }

    @Override
    public void setValue(Byte value) {
        this.actual = value;
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
            return hum;
        }
        return null;
    }
}

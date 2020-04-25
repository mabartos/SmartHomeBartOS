package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public class TemperatureData extends CapabilityDataWithValue<Double> {

    @JsonCreator
    public TemperatureData(@JsonProperty("actual") Double actualTemperature) {
        super(actualTemperature);
    }

    @Override
    public Double getValue() {
        return actual;
    }

    @Override
    public void setValue(Double value) {
        this.actual = value;
    }

    public static TemperatureData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, TemperatureData.class);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof TemperatureCapModel) {
            super.editModel(model);
            TemperatureCapModel temp = (TemperatureCapModel) model;
            temp.setType(CapabilityType.TEMPERATURE);
            return temp;
        }
        return null;
    }
}

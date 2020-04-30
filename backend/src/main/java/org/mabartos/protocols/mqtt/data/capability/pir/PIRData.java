package org.mabartos.protocols.mqtt.data.capability.pir;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.pir.PIRCapModel;
import org.mabartos.protocols.mqtt.data.capability.CapabilityDataWithState;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PIRData extends CapabilityDataWithState {

    public PIRData(@JsonProperty("isTurnedOn") boolean isTurnedOn) {
        super(isTurnedOn);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof PIRCapModel) {
            super.editModel(model);
            return model;
        }
        return null;
    }

    public static PIRData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, PIRData.class);
    }
}

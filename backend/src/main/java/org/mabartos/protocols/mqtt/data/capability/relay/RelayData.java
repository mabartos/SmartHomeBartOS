package org.mabartos.protocols.mqtt.data.capability.relay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.relay.RelayCapModel;
import org.mabartos.protocols.mqtt.data.capability.CapabilityDataWithState;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelayData extends CapabilityDataWithState {

    @JsonCreator
    public RelayData(@JsonProperty("isTurnedOn") boolean isTurnedOn) {
        super(isTurnedOn);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof RelayCapModel) {
            super.editModel(model);
            return model;
        }
        return null;
    }

    public static RelayData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, RelayData.class);
    }
}

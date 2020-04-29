package org.mabartos.protocols.mqtt.data.capability.extern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.extern.ExternBtnCapModel;
import org.mabartos.protocols.mqtt.data.capability.CapabilityDataWithState;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternBtnData extends CapabilityDataWithState {

    @JsonCreator
    public ExternBtnData(@JsonProperty("isTurnedOn") boolean isTurnedOn) {
        super(isTurnedOn);
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof ExternBtnCapModel) {
            super.editModel(model);
            return model;
        }
        return null;
    }

    public static ExternBtnData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, ExternBtnData.class);
    }
}

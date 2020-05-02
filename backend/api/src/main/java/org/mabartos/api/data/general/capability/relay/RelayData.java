package org.mabartos.api.data.general.capability.relay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithState;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.relay.RelayCapModel;

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
        return SerializeUtils.fromJson(json, RelayData.class);
    }
}

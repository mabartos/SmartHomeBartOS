package org.mabartos.api.data.general.capability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mabartos.api.data.mqtt.ConvertableToModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CapabilityData implements ConvertableToModel {

    public CapabilityData() {
    }
}

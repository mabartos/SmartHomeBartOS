package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mabartos.protocols.mqtt.data.general.ConvertableToModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CapabilityData implements ConvertableToModel {

    public CapabilityData() {
    }
}

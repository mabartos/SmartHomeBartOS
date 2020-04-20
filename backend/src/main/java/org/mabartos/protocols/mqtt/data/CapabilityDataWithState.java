package org.mabartos.protocols.mqtt.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;

public abstract class CapabilityDataWithState extends CapabilityData {

    @JsonProperty("isTurnedOn")
    protected boolean isTurnedOn;

    @JsonCreator
    public CapabilityDataWithState(@JsonProperty("id") Long id,
                                   @JsonProperty("name") String name,
                                   @JsonProperty("type") CapabilityType type,
                                   @JsonProperty("isTurnedOn") boolean isTurnedOn) {
        super(id, name, type);
        this.isTurnedOn = isTurnedOn;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void setState(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }
}

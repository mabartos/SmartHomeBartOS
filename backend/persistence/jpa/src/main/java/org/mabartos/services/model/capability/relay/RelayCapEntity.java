package org.mabartos.services.model.capability.relay;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.relay.RelayCapModel;
import org.mabartos.services.model.capability.CapabilityEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class RelayCapEntity extends CapabilityEntity implements RelayCapModel {

    @Column
    private boolean isTurnedOn = false;

    public RelayCapEntity() {
    }

    public RelayCapEntity(String name, Integer pin) {
        super(name, CapabilityType.RELAY, pin);
    }

    @Override
    @JsonProperty("isTurnedOn")
    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    @Override
    public void setState(boolean state) {
        this.isTurnedOn = state;
    }

    @Override
    public void changeState() {
        isTurnedOn = !isTurnedOn;
    }
}

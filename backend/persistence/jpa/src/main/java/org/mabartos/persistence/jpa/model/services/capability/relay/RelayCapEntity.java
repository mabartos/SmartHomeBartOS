/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.relay;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.relay.RelayCapModel;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;
import org.mabartos.persistence.jpa.model.services.events.trigger.TriggerEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RelayCapEntity extends CapabilityEntity implements RelayCapModel {

    @Column
    private boolean isTurnedOn = false;

    @Column
    @OneToMany(targetEntity = TriggerEntity.class, mappedBy = "triggerCap")
    private Set<TriggerModel> attachedTriggers = new HashSet<>();

    public RelayCapEntity() {
    }

    public RelayCapEntity(String name, Integer pin) {
        super(name, CapabilityType.RELAY, pin);
    }

    public RelayCapEntity(CapabilityModel model) {
        this(model.getName(), model.getPin());
    }

    @Override
    @JsonProperty(JsonCapNames.STATE)
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

    @Override
    public Set<TriggerModel> getAttachedTriggers() {
        return attachedTriggers;
    }

    @Override
    public void setAttachedTriggers(Set<TriggerModel> triggers) {
        this.attachedTriggers = triggers;
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.light;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.capability.JsonCapNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.light.LightCapModel;
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
public class LightCapEntity extends CapabilityEntity implements LightCapModel {

    @Column
    private Byte intensity = 0;

    @Column
    private Byte minIntensity = 0;

    @Column
    private boolean isTurnedOn = false;

    @Column
    @OneToMany(targetEntity = TriggerEntity.class, mappedBy = "triggerCap")
    private Set<TriggerModel> attachedTriggers = new HashSet<>();

    public LightCapEntity() {
    }

    public LightCapEntity(String name, Integer pin) {
        super(name, CapabilityType.LIGHT, pin);
    }

    public LightCapEntity(CapabilityModel model) {
        this(model.getName(), model.getPin());
    }

    public Byte getIntensity() {
        return intensity;
    }

    public void setIntensity(Byte intensity) {
        if (checkValidValue(intensity)) {
            this.intensity = intensity;
        }
    }

    public Byte getMinIntensity() {
        return minIntensity;
    }

    public void setMinIntensity(Byte minIntensity) {
        if (checkValidValue(minIntensity)) {
            this.minIntensity = minIntensity;
        }
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
        this.isTurnedOn = !this.isTurnedOn;
    }

    @Override
    public Byte getValue() {
        return getIntensity();
    }

    @Override
    public void setValue(Byte value) {
        setIntensity(value);
    }

    @Override
    public Set<TriggerModel> getAttachedTriggers() {
        return attachedTriggers;
    }

    @Override
    public void setAttachedTriggers(Set<TriggerModel> triggers) {
        this.attachedTriggers=triggers;
    }
}

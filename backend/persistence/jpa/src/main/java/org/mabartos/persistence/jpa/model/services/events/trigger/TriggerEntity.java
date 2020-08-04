/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.events.trigger;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.model.capability.InputCapModel;
import org.mabartos.api.model.events.trigger.InputTriggerStates;
import org.mabartos.api.model.events.trigger.OutputTriggerStates;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Triggers")
@Cacheable
public class TriggerEntity extends PanacheEntityBase implements TriggerModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @ManyToOne
    private CapabilityEntity capability;

    @Enumerated
    private InputTriggerStates inputState;

    @Enumerated
    private OutputTriggerStates outputState;

    public TriggerEntity() {
    }

    public TriggerEntity(InputCapModel capability, InputTriggerStates inputState, OutputTriggerStates outputState) {
        this.capability = (CapabilityEntity) capability;
        this.inputState = inputState;
        this.outputState = outputState;
    }

    @Override
    public InputCapModel getCapability() {
        if (capability instanceof InputCapModel) {
            return (InputCapModel) capability;
        }
        return null;
    }

    @Override
    public InputTriggerStates getInputState() {
        return inputState;
    }

    @Override
    public OutputTriggerStates getOutputState() {
        return outputState;
    }
}

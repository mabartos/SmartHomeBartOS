/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.events.trigger;

import com.google.common.base.Objects;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.InputCapModel;
import org.mabartos.api.model.capability.OutputCapModel;
import org.mabartos.api.model.events.trigger.InputTriggerStates;
import org.mabartos.api.model.events.trigger.OutputTriggerStates;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Triggers")
@Cacheable
@NamedQueries({
        @NamedQuery(name = "getInputTriggersByCapID", query = "select trigger from TriggerEntity trigger join fetch trigger.triggerCap join fetch trigger.resultCap where trigger.triggerCap.id = :id"),
        @NamedQuery(name = "getOutputTriggersByCapID", query = "select trigger from TriggerEntity trigger join fetch trigger.triggerCap join fetch trigger.resultCap where trigger.resultCap.id = :id"),
        @NamedQuery(name = "deleteTriggersWithCapID", query = "delete from TriggerEntity trig where trig.id = :capID or trig.id = :capID")
})
public class TriggerEntity extends PanacheEntityBase implements TriggerModel {

    @Id
    @GeneratedValue
    @Column(name = "TRIGGER_ID")
    private Long id;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CapabilityEntity.class)
    private CapabilityModel triggerCap;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CapabilityEntity.class)
    private CapabilityModel resultCap;

    @Enumerated
    private InputTriggerStates inputState;

    @Enumerated
    private OutputTriggerStates outputState;

    public TriggerEntity() {
    }

    public TriggerEntity(InputCapModel triggerCap, OutputCapModel resultCap, InputTriggerStates inputState, OutputTriggerStates outputState) {
        this.triggerCap = triggerCap;
        this.resultCap = resultCap;
        this.inputState = inputState;
        this.outputState = outputState;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public InputCapModel getTriggerCap() {
        return (InputCapModel) triggerCap;
    }

    @Override
    public void setTriggerCap(InputCapModel capability) {
        this.triggerCap = capability;
    }

    @Override
    public OutputCapModel getResultCap() {
        return (OutputCapModel) resultCap;
    }

    @Override
    public void setResultCap(OutputCapModel capability) {
        this.resultCap = capability;
    }

    @Override
    public InputTriggerStates getInputState() {
        return inputState;
    }

    @Override
    public void setInputState(InputTriggerStates state) {
        this.inputState = state;
    }


    @Override
    public OutputTriggerStates getOutputState() {
        return outputState;
    }

    @Override
    public void setOutputState(OutputTriggerStates state) {
        this.outputState = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof TriggerEntity)) {
            return false;
        } else {
            TriggerEntity entity = (TriggerEntity) obj;
            return getID().equals(entity.getID()) &&
                    getTriggerCap().equals(entity.getTriggerCap()) &&
                    getResultCap().equals(entity.getResultCap()) &&
                    getInputState().equals(entity.getInputState()) &&
                    getOutputState().equals(entity.getOutputState());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, inputState, outputState, resultCap, triggerCap);
    }
}

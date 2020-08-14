/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.triggers;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.controller.capability.triggers.TriggerData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.InputCapModel;
import org.mabartos.api.model.capability.OutputCapModel;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.capability.triggers.TriggerService;
import org.mabartos.persistence.jpa.model.services.events.trigger.TriggerEntity;
import org.mabartos.persistence.jpa.repository.TriggerRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class TriggerServiceImpl extends CRUDServiceImpl<TriggerModel, TriggerEntity, TriggerRepository, Long> implements TriggerService {

    AppServices services;

    public void start(@Observes StartupEvent event) {
    }

    public TriggerServiceImpl(TriggerRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    @Override
    public Set<TriggerModel> getAll(Long capabilityID) {
        Set<TriggerModel> triggers = new HashSet<>(getInputTriggersByCapID(capabilityID));
        triggers.addAll(getOutputTriggersByCapID(capabilityID));
        return triggers;
    }

    @Override
    public TriggerModel create(TriggerData data) {
        if (data != null) {
            CapabilityModel input = services.capabilities().findByID(data.getInputCapID());
            CapabilityModel output = services.capabilities().findByID(data.getOutputCapID());
            if (input instanceof InputCapModel && output instanceof OutputCapModel) {
                return create(new TriggerEntity((InputCapModel) input, (OutputCapModel) output, data.getInputState(), data.getOutputState()));
            }
        }
        return null;
    }

    @Override
    public TriggerModel update(Long id, TriggerData data) {
        TriggerModel model = findByID(id);
        if (model != null && data != null) {
            if (!model.getTriggerCap().getID().equals(data.getInputCapID())) {
                CapabilityModel input = services.capabilities().findByID(data.getInputCapID());
                if (input instanceof InputCapModel) {
                    model.setTriggerCap((InputCapModel) input);
                }
            }

            if (!model.getResultCap().getID().equals(data.getOutputCapID())) {
                CapabilityModel input = services.capabilities().findByID(data.getOutputCapID());
                if (input instanceof OutputCapModel) {
                    model.setResultCap((OutputCapModel) input);
                }
            }

            model.setInputState(data.getInputState());
            model.setOutputState(data.getOutputState());

            return model;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<TriggerModel> getInputTriggersByCapID(Long capabilityID) {
        Query query = getEntityManager().createNamedQuery("getInputTriggersByCapID");
        query.setParameter("id", capabilityID);
        return new HashSet<TriggerModel>(query.getResultList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<TriggerModel> getOutputTriggersByCapID(Long capabilityID) {
        Query query = getEntityManager().createNamedQuery("getOutputTriggersByCapID");
        query.setParameter("id", capabilityID);
        return new HashSet<TriggerModel>(query.getResultList());
    }

    @Override
    public int deleteAllTriggersFromCap(Long capabilityID) {
        Query query = getEntityManager().createNamedQuery("deleteTriggersWithCapID");
        query.setParameter("capID", capabilityID);
        return query.executeUpdate();
    }
}

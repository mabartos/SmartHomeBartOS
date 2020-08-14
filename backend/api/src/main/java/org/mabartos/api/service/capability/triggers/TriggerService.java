/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability.triggers;

import org.mabartos.api.controller.capability.triggers.TriggerData;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.service.CRUDService;

import java.util.Set;

public interface TriggerService extends CRUDService<TriggerModel, Long> {

    Set<TriggerModel> getAll(Long capabilityID);

    TriggerModel create(TriggerData data);

    TriggerModel update(Long id, TriggerData data);

    Set<TriggerModel> getInputTriggersByCapID(Long capabilityID);

    Set<TriggerModel> getOutputTriggersByCapID(Long capabilityID);

    int deleteAllTriggersFromCap(Long capabilityID);
}

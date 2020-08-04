/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability;

import org.mabartos.api.model.events.trigger.TriggerModel;

import java.util.Set;

public interface OutputCapModel extends CapabilityModel {

    Set<TriggerModel> getTriggers();

    void addTrigger(TriggerModel trigger);

    void removeTrigger(Long id);

    void editTrigger(Long id, TriggerModel trigger);
}

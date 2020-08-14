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

    Set<TriggerModel> getAttachedTriggers();

    void setAttachedTriggers(Set<TriggerModel> triggers);
}

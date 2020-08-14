/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability;

import org.mabartos.api.model.events.trigger.TriggerModel;

import java.util.Collections;
import java.util.Set;

public interface InputCapModel extends CapabilityModel {

    default Set<TriggerModel> getTriggers() {
        return Collections.emptySet();
    }

    default void setTriggers(Set<TriggerModel> triggers) {
        // NOP
    }
}

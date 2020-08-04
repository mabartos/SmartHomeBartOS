/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.events.trigger;

import org.mabartos.api.model.capability.InputCapModel;

public interface TriggerModel {
    InputCapModel getCapability();

    InputTriggerStates getInputState();

    OutputTriggerStates getOutputState();
}

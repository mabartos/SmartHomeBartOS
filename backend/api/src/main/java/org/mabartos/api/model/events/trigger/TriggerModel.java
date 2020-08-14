/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.events.trigger;

import org.mabartos.api.common.Identifiable;
import org.mabartos.api.model.capability.InputCapModel;
import org.mabartos.api.model.capability.OutputCapModel;

public interface TriggerModel extends Identifiable<Long> {

    InputCapModel getTriggerCap();

    void setTriggerCap(InputCapModel capability);

    OutputCapModel getResultCap();

    void setResultCap(OutputCapModel capability);

    InputTriggerStates getInputState();

    void setInputState(InputTriggerStates state);

    OutputTriggerStates getOutputState();

    void setOutputState(OutputTriggerStates state);
}

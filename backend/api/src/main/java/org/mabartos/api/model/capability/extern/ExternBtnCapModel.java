/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability.extern;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.service.capability.HasState;

public interface ExternBtnCapModel extends CapabilityModel, HasState {

    String HAS_TWO_STATES_JSON = "hasTwoStates";

    boolean hasTwoStates();

    void setHasTwoStates(boolean state);
}

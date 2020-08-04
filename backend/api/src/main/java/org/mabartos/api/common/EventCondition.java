/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.common;

import org.mabartos.api.service.capability.HasNumberValue;
import org.mabartos.api.service.capability.HasState;

public class EventCondition {

    public static boolean isTurnedOn(HasState capability, boolean state) {
        return capability != null && capability.isTurnedOn() == state;
    }

    public static <Value extends Number> boolean isEqualTo(HasNumberValue<Value> capability, Value value) {
        return capability != null && capability.getValue().equals(value);
    }

    public static <Value extends Number & Comparable<Value>> boolean isGreaterThan(HasNumberValue<Value> capability, Value value) {
        return capability != null && value.compareTo(capability.getValue()) > 0;
    }

    public static <Value extends Number & Comparable<Value>> boolean isLessThan(HasNumberValue<Value> capability, Value value) {
        return capability != null && value.compareTo(capability.getValue()) < 0;
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.capability;

import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.service.capability.CapabilityType;

import java.util.Collections;
import java.util.Set;

public interface CapabilityModel extends IdentifiableName<Long> {

    void setName(String name);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    CapabilityType getType();

    void setType(CapabilityType type);

    int getPin();

    void setPin(int pin);

    DeviceModel getDevice();

    Long getDeviceID();

    void setDevice(DeviceModel device);

    boolean isActive();

    default Set<TriggerModel> getTriggers() {
        return Collections.emptySet();
    }
}

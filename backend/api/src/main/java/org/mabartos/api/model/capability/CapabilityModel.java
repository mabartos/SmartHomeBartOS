package org.mabartos.api.model.capability;

import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.model.device.DeviceModel;

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
}

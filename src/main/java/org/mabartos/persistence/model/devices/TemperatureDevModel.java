package org.mabartos.persistence.model.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.common.DeviceWithValue;

import javax.persistence.Entity;

@Entity
public class TemperatureDevModel extends DeviceWithValue {

    public TemperatureDevModel(String name) {
        super(name, DeviceType.HUMIDITY);
    }

}

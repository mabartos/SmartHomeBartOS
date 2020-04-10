package org.mabartos.protocols.mqtt.data;

import org.mabartos.persistence.model.CapabilityModel;

public interface ConvertableToModel {
    CapabilityModel editModel(CapabilityModel model);
}

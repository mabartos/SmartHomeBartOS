package org.mabartos.api.data.mqtt;

import org.mabartos.api.model.capability.CapabilityModel;

public interface ConvertableToModel {
    CapabilityModel editModel(CapabilityModel model);
}

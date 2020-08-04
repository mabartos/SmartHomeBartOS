/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability;

import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.persistence.jpa.model.services.capability.extern.ExternBtnCapEntity;
import org.mabartos.persistence.jpa.model.services.capability.humidity.HumidityCapEntity;
import org.mabartos.persistence.jpa.model.services.capability.light.LightCapEntity;
import org.mabartos.persistence.jpa.model.services.capability.pir.PIRCapEntity;
import org.mabartos.persistence.jpa.model.services.capability.relay.RelayCapEntity;
import org.mabartos.persistence.jpa.model.services.capability.temperature.TemperatureCapEntity;

public class CapabilityUtils {
    public static CapabilityModel getEntityInstance(CapabilityModel model) {
        if (model != null) {
            switch (model.getType()) {
                case NONE:
                    break;
                case TEMPERATURE:
                    return new TemperatureCapEntity(model);
                case HUMIDITY:
                    return new HumidityCapEntity(model);
                case LIGHT:
                    return new LightCapEntity(model);
                case RELAY:
                    return new RelayCapEntity(model);
                case SOCKET:
                    break;
                case EXTERN_BTN:
                    return new ExternBtnCapEntity(model);
                case PIR:
                    return new PIRCapEntity(model);
                case GAS_SENSOR:
                    break;
                case STATISTICS:
                    break;
                case AIR_CONDITIONER:
                    break;
                case THERMOSTAT:
                    break;
                case OTHER:
                    break;
                default:
            }
        }
        return null;
    }
}

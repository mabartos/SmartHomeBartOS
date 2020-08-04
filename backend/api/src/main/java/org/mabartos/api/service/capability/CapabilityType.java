/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.capability;

import java.util.Arrays;

public enum CapabilityType {
    NONE("none"),
    TEMPERATURE("temp"),
    HUMIDITY("humi"),
    LIGHT("light"),
    RELAY("relay"),
    EXTERN_BTN("extern-btn"),
    SOCKET("socket"),
    PIR("pir"),
    GAS_SENSOR("gas"),
    STATISTICS("stats"),
    AIR_CONDITIONER("ac"),
    THERMOSTAT("thermo"),
    OTHER("other");

    private String name;

    CapabilityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return "/" + name;
    }

    public static String getTopic(CapabilityType type) {
        return "/" + type.getName();
    }

    public static CapabilityType getByName(String name) {
        return Arrays.stream(CapabilityType.values()).filter(f -> f.getName().equals(name)).findFirst().orElse(null);
    }
}

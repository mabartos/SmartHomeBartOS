package org.mabartos.general;

public enum CapabilityType {
    NONE("none"),
    TEMPERATURE("temp"),
    HUMIDITY("hum"),
    HEATER("heater"),
    LIGHT("light"),
    RELAY("relay"),
    SOCKET("socket"),
    PIR("pir"),
    GAS_SENSOR("gas"),
    STATISTICS("stats"),
    AIRCONDITIONER("ac"),
    OTHER("other");

    private String topic;

    CapabilityType(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public static String getTopic(CapabilityType type) {
        return type.getTopic();
    }
}

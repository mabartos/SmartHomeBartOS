package org.mabartos.general;

public enum DeviceType {
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

    DeviceType(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}

package org.mabartos.protocols.mqtt.topics;

public enum Topics {

    HOME_TOPIC("homes"),
    DEVICE_TOPIC("devices");

    private String name;

    Topics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return "/" + name;
    }

}

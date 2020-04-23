package org.mabartos.protocols.mqtt.topics;

public enum Topics {

    HOME_TOPIC("homes"),
    ROOM_TOPIC("rooms"),
    DEVICE_TOPIC("devices"),

    CREATE_TOPIC("create"),
    CONNECT_TOPIC("connect"),

    RESPONSE_TOPIC("resp");

    private String name;

    Topics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return this.equals(HOME_TOPIC) ? name : "/" + name;
    }

}

package org.mabartos.streams.mqtt.topics;

public enum Topics {

    HOME_TOPIC("homes");

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

package org.mabartos.protocols.mqtt.topics;

import java.util.Arrays;

public enum CRUDTopicType {
    CONNECT("connect"),
    CREATE("create"),
    REMOVE("remove"),
    UPDATE("update");

    private String name;

    CRUDTopicType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return "/" + name;
    }

    public static CRUDTopicType getByName(String name) {
        return Arrays.stream(CRUDTopicType.values()).filter(f -> f.getName().equals(name)).findFirst().orElse(null);
    }
}

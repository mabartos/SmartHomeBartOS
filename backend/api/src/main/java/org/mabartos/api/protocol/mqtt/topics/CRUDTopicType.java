/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.topics;

import java.util.Arrays;

public enum CRUDTopicType {
    CONNECT("connect"),
    CREATE("create"),
    REMOVE_FROM_HOME("remove-from-org.mabartos.home"),
    UPDATE("update"),
    DELETE("delete"),
    LOGOUT("logout"),
    GET_ROOM("get-org.mabartos.room");

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

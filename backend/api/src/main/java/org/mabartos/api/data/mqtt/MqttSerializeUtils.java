/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.mqtt;

import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.protocol.mqtt.exceptions.WrongMessageTopicException;

public class MqttSerializeUtils implements MqttSerializable {

    private SerializeUtils utils;

    public MqttSerializeUtils(Object object) {
        this.utils = new SerializeUtils(object);
    }

    @Override
    public String toJson() {
        try {
            return this.utils.toJson();
        } catch (RuntimeException e) {
            throw new WrongMessageTopicException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return SerializeUtils.fromJson(json, type);
        } catch (RuntimeException e) {
            throw new WrongMessageTopicException(e);
        }
    }

}

package org.mabartos.streams.mqtt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mabartos.streams.mqtt.messages.MqttSerializable;

public class MqttSerializeUtils implements MqttSerializable {

    private Object object;

    public MqttSerializeUtils(Object object) {
        this.object = object;
    }

    @Override
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T) mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}

package mqtt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import mqtt.exceptions.WrongMessageTopicException;
import mqtt.messages.MqttSerializable;

public class MqttSerializeUtils implements MqttSerializable {

    private Object object;

    public MqttSerializeUtils(Object object) {
        this.object = object;
    }

    @Override
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new WrongMessageTopicException();
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new WrongMessageTopicException();
        }
    }

}

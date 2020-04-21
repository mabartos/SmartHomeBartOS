package org.mabartos.protocols.mqtt.data.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.protocols.mqtt.data.general.MqttSerializable;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonPropertyOrder({"msgID", "id", "name"})
public class ConnectRequestData implements MqttSerializable {

    @JsonProperty("msgID")
    private Long msgID;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public ConnectRequestData(@JsonProperty("msgID") Long msgID,
                              @JsonProperty("id") Long id,
                              @JsonProperty("name") String name) {
        this.msgID = msgID;
        this.id = id;
        this.name = name;
    }

    public Long getMsgID() {
        return msgID;
    }

    public void setMsgID(Long msgID) {
        this.msgID = msgID;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ConnectRequestData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, ConnectRequestData.class);
    }
}

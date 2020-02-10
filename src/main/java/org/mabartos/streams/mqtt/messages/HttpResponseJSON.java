package org.mabartos.streams.mqtt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.mabartos.streams.mqtt.utils.MqttSerializeUtils;

public class HttpResponseJSON implements MqttSerializable {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("reasonPhrase")
    private String reasonPhrase;

    @JsonProperty("message")
    private String message = "";

    public HttpResponseJSON(Integer statusCode) {
        setCode(statusCode);
    }

    public HttpResponseJSON(Integer statusCode, String message) {
        this(statusCode);
        this.message = message;
    }

    public HttpResponseJSON(HttpResponseStatus status) {
        setCode(status);
    }

    public HttpResponseJSON(HttpResponseStatus status, String message) {
        this(status);
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        HttpResponseStatus response = HttpResponseStatus.valueOf(code);
        this.code = response.code();
        this.reasonPhrase = response.reasonPhrase();
    }

    public void setCode(HttpResponseStatus status) {
        this.code = status.code();
        this.reasonPhrase = status.reasonPhrase();
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toJson() {
        return new MqttSerializeUtils(this).toJson();
    }
}

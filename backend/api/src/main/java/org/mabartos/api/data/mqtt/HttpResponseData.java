/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpResponseData implements MqttSerializable {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("reasonPhrase")
    private String reasonPhrase;

    @JsonProperty("message")
    private String message = "";

    public HttpResponseData(Integer statusCode) {
        setCode(statusCode);
    }

    public HttpResponseData(Integer statusCode, String message) {
        this(statusCode);
        this.message = message;
    }

    public HttpResponseData(HttpResponseStatus status) {
        setCode(status);
    }

    public HttpResponseData(HttpResponseStatus status, String message) {
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

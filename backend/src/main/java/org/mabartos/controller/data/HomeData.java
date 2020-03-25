package org.mabartos.controller.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.protocols.mqtt.data.ConvertableToModel;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public class HomeData implements ConvertableToModel<HomeModel> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("brokerURL")
    private String brokerURL;

    @JsonProperty("mqttClientID")
    private Long mqttClientID;

    @JsonCreator
    public HomeData(@JsonProperty("id") Long id,
                    @JsonProperty("name") String name,
                    @JsonProperty("brokerURL") String brokerURL,
                    @JsonProperty("mqttClientID") Long mqttClientID
    ) {
        this.id = id;
        this.name = name;
        this.brokerURL = brokerURL;
        this.mqttClientID = mqttClientID;
    }

    @Override
    public HomeModel toModel() {
        HomeModel created = new HomeModel(name, brokerURL);
        created.setID(id);
        return created;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public Long getMqttClientID() {
        return mqttClientID;
    }

    public static HomeData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, HomeData.class);
    }
}

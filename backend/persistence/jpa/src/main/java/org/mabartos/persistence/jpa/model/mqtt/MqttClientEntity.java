/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.mqtt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.mqtt.MqttClientModel;
import org.mabartos.persistence.jpa.model.services.home.HomeEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MqttClients")
@Cacheable
public class MqttClientEntity extends PanacheEntityBase implements MqttClientModel {

    @Id
    @GeneratedValue
    @Column(name = "mqttClientID")
    Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mqttClient")
    private HomeEntity home;

    @Column
    private String brokerURL;

    @Column
    private String topic;

    @Column
    private boolean brokerActive = false;

    public MqttClientEntity() {
    }

    public MqttClientEntity(HomeModel home, String brokerURL) {
        this.home = (HomeEntity) home;
        this.name = home.getName() + "_client";
        this.brokerURL = brokerURL;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public HomeModel getHome() {
        return home;
    }

    @JsonProperty(JsonPropertyNames.HOME_ID)
    public Long getHomeID() {
        return home.getID();
    }

    public void setHome(HomeModel home) {
        this.home = (HomeEntity) home;
    }

    public String getTopic() {
        if (topic == null && home != null)
            return "homes/" + home.getID();
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public boolean isBrokerActive() {
        return brokerActive;
    }

    public void setBrokerActive(boolean state) {
        this.brokerActive = state;
    }
}

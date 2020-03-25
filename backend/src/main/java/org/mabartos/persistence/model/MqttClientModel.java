package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.interfaces.Identifiable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "MqttClients")
@Cacheable
public class MqttClientModel extends PanacheEntityBase implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "mqttClientID")
    Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mqttClient")
    private HomeModel home;

    @Column
    private String brokerURL;

    @Column
    private String topic;

    @Column
    private boolean brokerActive = false;

    public MqttClientModel() {
    }

    public MqttClientModel(HomeModel home, String brokerURL) {
        this.home = home;
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

    @JsonProperty("homeId")
    public Long getHomeID() {
        return home.getID();
    }

    public void setHome(HomeModel home) {
        this.home = home;
    }

    public String getTopic() {
        if (topic == null && home != null)
            return "/homes/" + home.getID();
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

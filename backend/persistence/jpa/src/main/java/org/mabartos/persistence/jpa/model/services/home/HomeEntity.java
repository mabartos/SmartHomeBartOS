/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.home;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.mqtt.MqttClientModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.persistence.jpa.model.mqtt.MqttClientEntity;
import org.mabartos.persistence.jpa.model.services.device.DeviceEntity;
import org.mabartos.persistence.jpa.model.services.room.RoomEntity;
import org.mabartos.persistence.jpa.model.services.user.UserEntity;
import org.mabartos.persistence.jpa.model.services.user.UserRoleEntity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Homes")
@Cacheable
@JsonIgnoreProperties(value = {JsonPropertyNames.MQTT_CLIENT_ID, JsonPropertyNames.USERS_COUNT})
@JsonPropertyOrder({JsonPropertyNames.ID, JsonPropertyNames.NAME, JsonPropertyNames.ACTIVE})
public class HomeEntity extends PanacheEntityBase implements HomeModel {

    @Id
    @GeneratedValue
    @Column(name = "HOME_ID")
    Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String brokerURL;

    @OneToMany(targetEntity = DeviceEntity.class, mappedBy = "home", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<DeviceModel> unAssignedDevices = new HashSet<>();

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "homesSet", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<UserModel> usersSet = new HashSet<>();

    @OneToMany(targetEntity = RoomEntity.class, mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<RoomModel> roomsSet = new HashSet<>();

    @OneToMany(targetEntity = HomeInvitationEntity.class, mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<HomeInvitationModel> invitations = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mqttClient", referencedColumnName = "mqttClientID")
    private MqttClientEntity mqttClient;

    @OneToMany(targetEntity = UserRoleEntity.class, mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<UserRoleModel> roles = new HashSet<>();

    public HomeEntity() {
    }

    public HomeEntity(String name) {
        this();
        this.name = name;
    }

    public HomeEntity(String name, String brokerURL) {
        this(name);
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

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
        if (this.mqttClient != null) {
            this.mqttClient.setBrokerURL(brokerURL);
        }
    }

    /* USERS */
    @JsonIgnore
    public Set<UserModel> getUsers() {
        return usersSet;
    }

    public boolean addUser(UserModel user) {
        return addUser(user, UserRole.HOME_MEMBER);
    }

    public boolean addUser(UserModel user, UserRole role) {
        UserEntity userEntity = (UserEntity) user;
        if (usersSet.add(user)) {
            if (!isContainedInRoles(userEntity)) {
                return roles.add(new UserRoleEntity(userEntity, this, role));
            }
            return true;
        }
        return false;
    }

    private boolean isContainedInRoles(UserModel user) {
        if (roles != null && user != null && !roles.isEmpty()) {
            return roles.stream().anyMatch(f -> f.getUser().equals(user));
        }
        return false;
    }

    public boolean removeUser(UserModel user) {
        return (user != null && user.removeHome(this) && removeRoleForUser((UserEntity) user) && usersSet.remove(user));
    }

    private boolean removeRoleForUser(UserModel user) {
        return (user != null) && roles.removeIf(f -> f.getUser().equals(user));
    }

    /* ROLES */
    @JsonIgnore
    public Set<UserRoleModel> getUserRoles() {
        return roles;
    }

    @JsonIgnore
    public UserRole getUserRoleByID(UUID userID) {
        if (roles != null && userID != null) {
            return roles.stream()
                    .filter(f -> f.getUser().getID().equals(userID))
                    .map(UserRoleModel::getRole)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean changeRoleForUser(UserModel user, UserRole role) {
        UserRoleModel found = roles.stream()
                .filter(f -> f.getUser().equals(user))
                .findFirst()
                .orElse(null);
        if (found != null) {
            found.setRole(role);
            return true;
        }
        return false;
    }

    public boolean removeRoleForUser(UUID userID) {
        return roles.removeIf(f -> f.getUser().getID().equals(userID));
    }

    /* DEVICES */
    @JsonIgnore
    public Set<DeviceModel> getUnAssignedDevices() {
        return unAssignedDevices;
    }

    public boolean addDevice(DeviceModel device) {
        return unAssignedDevices.add(device);
    }

    public boolean removeDeviceFromHome(DeviceModel device) {
        return unAssignedDevices.remove(device);
    }

    public void removeAllDevices() {
        this.unAssignedDevices = Collections.emptySet();
    }

    /* ROOMS */
    public void clearRooms() {
        roomsSet = Collections.emptySet();
    }

    @Override
    @JsonIgnore
    public Set<RoomModel> getChildren() {
        return roomsSet;
    }

    @Override
    public boolean addChild(RoomModel child) {
        return roomsSet.add(child);
    }

    @Override
    public boolean removeChild(RoomModel child) {
        return roomsSet.remove(child);
    }

    @Override
    public boolean removeChildByID(Long id) {
        return roomsSet.removeIf(room -> room.getID().equals(id));
    }

    /* MQTT */
    @JsonIgnore
    public MqttClientModel getMqttClient() {
        return (MqttClientModel) mqttClient;
    }

    /* INVITATIONS */
    @JsonIgnore
    public Set<HomeInvitationModel> getInvitations() {
        return invitations;
    }

    public boolean addInvitation(HomeInvitationModel invitation) {
        return invitations.add(invitation);
    }

    public boolean removeInvitation(HomeInvitationModel invitation) {
        return invitations.remove(invitation);
    }

    public void removeAllInvitations() {
        invitations = Collections.emptySet();
    }

    /* COMPUTED */
    @JsonProperty(JsonPropertyNames.MQTT_CLIENT_ID)
    public Long getMqttClientID() {
        return mqttClient.getID();
    }

    @JsonProperty(JsonPropertyNames.USERS_COUNT)
    public Integer getUsersCount() {
        if (usersSet != null) {
            return usersSet.size();
        }
        return 0;
    }

    @JsonProperty(JsonPropertyNames.ACTIVE)
    public boolean isHomeActive() {
        if (mqttClient != null) {
            return mqttClient.isBrokerActive();
        }
        return false;
    }

    public void setMqttClient(MqttClientModel mqttClient) {
        this.mqttClient = (MqttClientEntity) mqttClient;
    }

    public void setMqttClient(MqttClientEntity mqttClient) {
        this.mqttClient = mqttClient;
    }

    /* MANAGE */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof HomeEntity))
            return false;
        else {
            HomeEntity object = (HomeEntity) obj;
            return (object.getID().equals(this.getID())
                    && object.getName().equals(this.getName())
                    && object.getUsers().equals(this.getUsers())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, getUsers());
    }
}

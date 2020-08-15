/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;
import org.mabartos.persistence.jpa.model.services.home.HomeEntity;
import org.mabartos.persistence.jpa.model.services.room.RoomEntity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Devices")
@Cacheable
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedQueries({
        @NamedQuery(name = "getAllDevices", query = "select dev from DeviceEntity dev join fetch dev.room join fetch dev.home"),
        @NamedQuery(name = "getDeviceByID", query = "select dev from DeviceEntity dev join fetch dev.room join fetch dev.home where dev.id=:id"),
        @NamedQuery(name = "setDeviceRoomToNull", query = "update DeviceEntity set room = null where room.id=:roomID"),
        @NamedQuery(name = "deleteDevicesFromHome", query = "delete from DeviceEntity where home.id=:homeID")
})
public class DeviceEntity extends PanacheEntityBase implements DeviceModel {

    @Id
    @GeneratedValue
    @Column(name = "DEVICE_ID")
    Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM")
    private RoomEntity room;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME")
    private HomeEntity home;

    @OneToMany(targetEntity = CapabilityEntity.class, mappedBy = "device", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<CapabilityModel> capabilities = new HashSet<>();

    @Column
    private boolean active = false;

    public DeviceEntity() {
    }

    public DeviceEntity(String name) {
        this.name = name;
    }

    public DeviceEntity(String name, Set<CapabilityModel> capabilities) {
        this(name);
        setCapabilities(capabilities);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public RoomModel getRoom() {
        return room;
    }

    @JsonProperty(JsonPropertyNames.ROOM_ID)
    public Long getRoomID() {
        return Optional.ofNullable(room.getID()).orElse(-1L);
    }

    @JsonProperty(JsonPropertyNames.HOME_ID)
    public Long getHomeID() {
        return Optional.ofNullable(home.getID()).orElse(-1L);
    }

    public void setRoom(RoomModel room) {
        this.room = (RoomEntity) room;
    }

    public String getTopic() {
        if (home != null) {
            return home.getMqttClient().getTopic() + DevicesResource.PATH + "/" + id;
        }
        return null;
    }

    @JsonIgnore
    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = (HomeEntity) home;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonIgnore
    public Set<String> getCapabilitiesName() {
        if (capabilities != null) {
            return capabilities
                    .stream()
                    .map(CapabilityModel::getName)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @JsonIgnore
    public Set<CapabilityModel> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<CapabilityModel> capabilities) {
        this.capabilities = capabilities;
        this.capabilities.forEach(f -> f.setDevice(this));
    }

    public boolean addCapability(CapabilityModel capability) {
        return capabilities.add(capability);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof DeviceEntity))
            return false;
        else {
            DeviceEntity object = (DeviceEntity) obj;

            if (object.getRoom() != null && this.getRoom() != null && !object.getRoom().equals(this.getRoom())) {
                return false;
            }

            return (object.getID().equals(this.getID())
                    && object.getName().equals(this.getName())
                    && object.getHome().equals(this.getHome())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, room, home);
    }

}

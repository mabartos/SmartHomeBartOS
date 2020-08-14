/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.common.RoomType;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.persistence.jpa.model.services.device.DeviceEntity;
import org.mabartos.persistence.jpa.model.services.events.trigger.TriggerEntity;
import org.mabartos.persistence.jpa.model.services.home.HomeEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
import java.util.UUID;

@Entity
@Table(name = "Rooms")
@Cacheable
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedQueries({
        @NamedQuery(name = "getAllRooms", query = "select room from RoomEntity room join fetch room.home join fetch room.devicesSet"),
        @NamedQuery(name = "getRoomByID", query = "select room from RoomEntity room join fetch room.home join fetch room.devicesSet where room.id=:id"),
        @NamedQuery(name = "deleteRoomByID", query = "delete from RoomEntity where id=:id"),
        @NamedQuery(name = "deleteRoomsFromHome", query = "delete from RoomEntity where home.id=:homeID")
})
public class RoomEntity extends PanacheEntityBase implements RoomModel {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID")
    Long id;

    @Column(nullable = false)
    private String name;

    //TODO Is it useful?
    @Column(nullable = false)
    @Enumerated
    private RoomType type = RoomType.NONE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private HomeEntity home;

    @Column
    @ElementCollection
    private Set<UUID> ownersID = new HashSet<>();

    @OneToMany(targetEntity = DeviceEntity.class, mappedBy = "room")
    private Set<DeviceModel> devicesSet = new HashSet<>();

    @OneToMany(targetEntity = TriggerEntity.class, mappedBy = "room")
    private Set<TriggerModel> triggers = new HashSet<>();

    public RoomEntity() {
    }

    public RoomEntity(String name) {
        this.name = name;
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

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    /* Owners */
    @JsonIgnore
    public Set<UUID> getOwnersID() {
        return ownersID;
    }

    public boolean addOwnerID(UUID id) {
        return ownersID.add(id);
    }

    public boolean removeOwnerID(UUID id) {
        return ownersID.remove(id);
    }

    public boolean isOwner(UUID id) {
        return !ownersID.isEmpty() && ownersID.stream().anyMatch(id::equals);
    }

    /* HOME */
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
        if (home != null) {
            this.home.addChild(this);
        }
    }

    @Override
    @JsonIgnore
    public Set<TriggerModel> getAllTriggers() {
        return this.triggers;
    }

    @JsonProperty("triggersCount")
    public Integer getTriggersCount() {
        return triggers.size();
    }

    @Override
    public void setTriggers(Set<TriggerModel> triggers) {
        this.triggers = triggers;
    }

    /* DEVICES */
    @Override
    @JsonIgnore
    public Set<DeviceModel> getChildren() {
        return devicesSet;
    }

    @Override
    public boolean addChild(DeviceModel child) {
        return devicesSet.add(child);
    }

    @Override
    public boolean removeChild(DeviceModel child) {
        return devicesSet.remove(child);
    }

    @Override
    public boolean removeChildByID(Long id) {
        return devicesSet.removeIf(device -> device.getID().equals(id));
    }

    public void removeAllChidlren() {
        devicesSet = Collections.emptySet();
    }

    /* COMPUTED */
    @JsonProperty("devicesCount")
    public Integer getDevicesCount() {
        if (devicesSet != null) {
            return devicesSet.size();
        }
        return 0;
    }

    /* MANAGE */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof RoomEntity))
            return false;
        else {
            RoomEntity room = (RoomEntity) obj;
            return (this.getID().equals(room.getID())
                    && this.getName().equals(room.getName())
                    && this.getHome().equals(room.getHome())
                    && this.getType().equals(room.getType())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, home, name, type);
    }
}

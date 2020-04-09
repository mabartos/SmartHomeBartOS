package org.mabartos.persistence.model.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.general.RoomType;
import org.mabartos.interfaces.HasChildren;
import org.mabartos.interfaces.IdentifiableName;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.home.HomeModel;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Rooms")
@Cacheable
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedQueries({
        @NamedQuery(name = "deleteRoomByID", query = "delete from RoomModel where id=:id"),
        @NamedQuery(name = "deleteRoomsFromHome", query = "delete from RoomModel where home.id=:homeID")
})
public class RoomModel extends PanacheEntityBase implements HasChildren<DeviceModel>, IdentifiableName<Long> {

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

    @ManyToOne
    @JoinColumn
    private HomeModel home;

    @Column
    @ElementCollection
    private Set<UUID> ownersID = new HashSet<>();

    @OneToMany(targetEntity = DeviceModel.class, mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<DeviceModel> devicesSet = new HashSet<>();

    public RoomModel() {
    }

    public RoomModel(String name) {
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

    @JsonProperty("homeID")
    public Long getHomeID() {
        return home.getID();
    }

    public void setHome(HomeModel home) {
        this.home = home;
        if (home != null) {
            this.home.addChild(this);
        }
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
        else if (!(obj instanceof RoomModel))
            return false;
        else {
            RoomModel room = (RoomModel) obj;
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

package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.controller.DeviceResource;
import org.mabartos.interfaces.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "Devices")
@Cacheable
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DeviceModel extends PanacheEntityBase implements Serializable, Identifiable {

    @Id
    @GeneratedValue
    @Column(name = "DEVICE_ID")
    protected Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM")
    private RoomModel room;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "HOME")
    private HomeModel home;

    @OneToMany(targetEntity = CapabilityModel.class,mappedBy = "device")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CapabilityModel> capabilities;

    public DeviceModel() {
    }

    public DeviceModel(String name) {
        this.name = name;
    }

    public DeviceModel(String name, List<CapabilityModel> capabilities) {
        this(name);
        this.capabilities = capabilities;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public String getTopic() {
        if (home != null) {
            return home.getTopic() + DeviceResource.DEVICE_PATH + "/" + id;
        }
        return null;
    }

    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = home;
    }

    @JsonIgnore
    public List<String> getCapabilitiesName() {
        if (capabilities != null) {
            return capabilities
                    .stream()
                    .map(CapabilityModel::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<CapabilityModel> getCapabilities() {
        return capabilities;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof DeviceModel))
            return false;
        else {
            DeviceModel object = (DeviceModel) obj;
            return (object.getID().equals(this.getID())
                    && object.getName().equals(this.getName())
                    && object.getRoom().equals(this.getRoom())
                    && object.getHome().equals(this.getHome())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, room, home);
    }

}

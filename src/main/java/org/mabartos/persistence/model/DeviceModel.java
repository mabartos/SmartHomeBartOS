package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.controller.DeviceResource;
import org.mabartos.general.DeviceType;
import org.mabartos.interfaces.Identifiable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Devices")
@Cacheable
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DeviceModel extends PanacheEntityBase implements Serializable, Identifiable {

    @Id
    @GeneratedValue
    @Column(name = "DEVICE_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated
    @Column(nullable = false)
    private DeviceType type = DeviceType.NONE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private RoomModel room;

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

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public String getTopic() {
        return DeviceResource.DEVICE_PATH + "/" + type.name().toLowerCase() + "/" + id;
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
                    && object.getType().equals(this.getType())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, room, type);
    }

}

package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.mabartos.general.DeviceType;
import org.mabartos.utils.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Devices")
public class DeviceModel extends PanacheEntity implements Serializable, Identifiable {

    @Column(nullable = false)
    public String name;

    @Enumerated
    @Column(nullable = false)
    public DeviceType type = DeviceType.NONE;

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
}

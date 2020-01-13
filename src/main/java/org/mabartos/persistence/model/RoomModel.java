package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.mabartos.general.RoomType;
import org.mabartos.utils.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Rooms")
public class RoomModel extends PanacheEntity implements Serializable, Identifiable {

    @Column(nullable = false)
    public String name;

    @Column
    public String imageURL;

    @Column(nullable = false)
    @Enumerated
    public RoomType type = RoomType.NONE;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id=id;
    }

    //TODO home,devices

}

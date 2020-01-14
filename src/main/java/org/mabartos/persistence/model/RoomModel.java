package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.general.RoomType;
import org.mabartos.utils.HasChildren;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Rooms")
public class RoomModel extends PanacheEntityBase implements HasChildren<DeviceModel> {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID")
    private Long id;

    @Column(nullable = false)
    public String name;

    @Column
    public String imageURL;

    @Column(nullable = false)
    @Enumerated
    public RoomType type = RoomType.NONE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private HomeModel home;

    @OneToMany(targetEntity = DeviceModel.class, mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DeviceModel> devicesList = new ArrayList<>();

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
        this.id=id;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = home;
    }

    @Override
    public List<DeviceModel> getChildren() {
        return devicesList;
    }

    @Override
    public boolean addChild(DeviceModel child) {
        return devicesList.add(child);
    }

    @Override
    public boolean removeChild(DeviceModel child) {
        return devicesList.remove(child);
    }

    @Override
    public boolean removeChildByID(Long id) {
        return devicesList.removeIf(device -> device.getID().equals(id));
    }

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
                    && this.getImageURL().equals(room.getImageURL())
                    && this.getType().equals(room.getType())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, home, imageURL, type);
    }
}

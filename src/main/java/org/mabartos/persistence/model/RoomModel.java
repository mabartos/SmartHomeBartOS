package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.general.RoomType;
import org.mabartos.utils.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(nullable = false)
    private HomeModel home;

    @OneToMany(targetEntity = DeviceModel.class, mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DeviceModel> listDevices = new ArrayList<>();

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

    public List<DeviceModel> getListDevices() {
        return listDevices;
    }

    public boolean addToRoomDevice(DeviceModel deviceModel) {
        return getListDevices().add(deviceModel);
    }
}

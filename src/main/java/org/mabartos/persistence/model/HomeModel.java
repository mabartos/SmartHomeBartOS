package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.utils.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Homes")
public class HomeModel extends PanacheEntity implements Serializable, Identifiable {

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    public String brokerURL;

    @Column
    public String imageURL;

    @OneToMany(targetEntity = RoomModel.class, mappedBy = "home", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RoomModel> roomsList;

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
        this.id = id;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<RoomModel> getRoomsList() {
        return roomsList;
    }

    public boolean addRoomToHome(RoomModel room) {
        return getRoomsList().add(room);
    }
}

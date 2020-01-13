package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.mabartos.utils.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Homes")
public class HomeModel extends PanacheEntity implements Serializable, Identifiable {

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    public String brokerURL;

    @Column
    public String imageURL;


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
}

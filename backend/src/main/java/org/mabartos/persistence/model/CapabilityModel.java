package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.general.CapabilityType;
import org.mabartos.interfaces.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "CAPABILITY")
@Entity
public class CapabilityModel extends PanacheEntityBase implements Serializable, Identifiable {

    @Id
    @GeneratedValue
    @Column(name = "CAPABILITY_ID")
    Long id;

    @Column(name = "NAME")
    private String name;

    @Enumerated
    private CapabilityType type;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "DEVICE")
    private DeviceModel device;

    public CapabilityModel() {
    }

    public CapabilityModel(String name, CapabilityType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CapabilityType getType() {
        return type;
    }

    public void setType(CapabilityType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else if (!(obj instanceof CapabilityModel))
            return false;
        else {
            CapabilityModel cap = (CapabilityModel) obj;
            return cap.getName().equals(this.getName())
                    && cap.getID().equals(this.getID())
                    && cap.getType().equals(this.getType());
        }
    }

    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}

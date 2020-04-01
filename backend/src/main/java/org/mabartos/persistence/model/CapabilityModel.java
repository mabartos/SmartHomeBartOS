package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.general.CapabilityType;
import org.mabartos.interfaces.Identifiable;

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

@Table(name = "CAPABILITY")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CapabilityModel extends PanacheEntityBase implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "NAME")
    private String name;

    @Column
    private boolean enabled = true;

    @Enumerated
    private CapabilityType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DEVICE_ID")
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CapabilityType getType() {
        return type;
    }

    public void setType(CapabilityType type) {
        this.type = type;
    }

    @JsonIgnore
    public DeviceModel getDevice() {
        return device;
    }

    @JsonProperty("deviceID")
    public Long getDeviceID() {
        return (device != null) ? device.getID() : -1;
    }

    public void setDevice(DeviceModel device) {
        device.addCapability(this);
        this.device = device;
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

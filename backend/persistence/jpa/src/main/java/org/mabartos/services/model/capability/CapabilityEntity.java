package org.mabartos.services.model.capability;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.services.model.device.DeviceEntity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "CAPABILITY")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({
        @NamedQuery(name = "deleteCapsFromDevice", query = "delete from CapabilityEntity where device.id=:deviceID")
})
public class CapabilityEntity extends PanacheEntityBase implements CapabilityModel {

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
    private DeviceEntity device;

    @Column
    private int pin;

    public CapabilityEntity() {
    }

    public CapabilityEntity(String name, CapabilityType type, Integer pin) {
        this.name = name;
        this.type = type;
        this.pin = pin;
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
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
        this.device = (DeviceEntity) device;
    }

    @JsonProperty("active")
    public boolean isActive() {
        return device.isActive();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else if (!(obj instanceof CapabilityEntity))
            return false;
        else {
            CapabilityEntity cap = (CapabilityEntity) obj;
            return cap.getName().equals(this.getName())
                    && cap.getID().equals(this.getID())
                    && cap.getType().equals(this.getType());
        }
    }

    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}

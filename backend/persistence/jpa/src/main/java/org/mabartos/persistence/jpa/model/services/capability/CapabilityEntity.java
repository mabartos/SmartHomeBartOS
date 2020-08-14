/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.persistence.jpa.model.services.device.DeviceEntity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import java.util.Optional;

@Table(name = "CAPABILITY")
@Entity
@Cacheable
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    @JsonProperty(JsonPropertyNames.DEVICE_ID)
    public Long getDeviceID() {
        return Optional.ofNullable(device.getID()).orElse(-1L);
    }

    public void setDevice(DeviceModel device) {
        device.addCapability(this);
        this.device = (DeviceEntity) device;
    }

    @JsonProperty(JsonPropertyNames.ACTIVE)
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

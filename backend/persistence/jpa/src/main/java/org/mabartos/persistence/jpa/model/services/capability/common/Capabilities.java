/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability.common;


import org.mabartos.api.model.capability.CapabilityModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Capabilities implements Serializable {

    private Set<CapabilityModel> caps;

    public Capabilities() {
        caps = new HashSet<>();
    }

    public Capabilities(CapabilityModel... capabilities) {
        this();
        addCapabilities(capabilities);
    }

    public Capabilities(Set<CapabilityModel> capabilities) {
        caps = capabilities;
    }

    public boolean addCapability(CapabilityModel capability) {
        return caps.add(capability);
    }

    public void addCapabilities(CapabilityModel... capabilities) {
        Arrays.stream(capabilities).forEach(this::addCapability);
    }

    public boolean removeCapability(CapabilityModel capability) {
        return caps.remove(capability);
    }

    public Set<CapabilityModel> getAll() {
        return caps;
    }

}

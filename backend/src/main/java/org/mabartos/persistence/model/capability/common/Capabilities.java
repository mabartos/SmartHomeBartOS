package org.mabartos.persistence.model.capability.common;

import org.mabartos.persistence.model.CapabilityModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Capabilities implements Serializable {

    private List<CapabilityModel> caps;

    public Capabilities() {
        caps = new ArrayList<>();
    }

    public Capabilities(CapabilityModel... capabilities) {
        this();
        addCapabilities(capabilities);
    }

    public Capabilities(List<CapabilityModel> capabilities) {
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

    /*public boolean removeCapabilityByID(Integer id) {
        return caps.removeIf(cap -> cap.getID()
                .equals(id));
    }

     */

    public List<CapabilityModel> getAll() {
        return caps;
    }

    /*public CapabilityModel getByIDinDevice(Integer id) {
        return caps.stream()
                .filter(f -> f.getIdInDevice().equals(id))
                .findFirst()
                .orElse(null);
    }

     */
}

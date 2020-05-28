/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.device;

import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;

import java.util.Set;

public interface DeviceModel extends IdentifiableName<Long> {

    void setName(String name);

    RoomModel getRoom();

    Long getRoomID();

    Long getHomeID();

    void setRoom(RoomModel room);

    String getTopic();

    HomeModel getHome();

    void setHome(HomeModel home);

    boolean isActive();

    void setActive(boolean active);

    Set<String> getCapabilitiesName();

    Set<CapabilityModel> getCapabilities();

    void setCapabilities(Set<CapabilityModel> capabilities);

    boolean addCapability(CapabilityModel capability);
}

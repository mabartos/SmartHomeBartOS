/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.utils;

import org.mabartos.api.controller.capability.CapabilityInfoData;
import org.mabartos.api.controller.capability.triggers.TriggerData;
import org.mabartos.api.controller.room.RoomData;
import org.mabartos.api.data.general.device.DeviceInfoData;
import org.mabartos.api.data.general.home.HomeData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.persistence.jpa.model.services.capability.CapabilityEntity;
import org.mabartos.persistence.jpa.model.services.device.DeviceEntity;
import org.mabartos.persistence.jpa.model.services.events.trigger.TriggerEntity;
import org.mabartos.persistence.jpa.model.services.home.HomeEntity;
import org.mabartos.persistence.jpa.model.services.room.RoomEntity;

public class DataToModelBase {

    /* HOME */
    public static HomeModel toHomeModel(HomeData data) {
        return toHomeModel(data, null);
    }

    public static HomeModel toHomeModel(HomeData data, HomeModel home) {
        HomeModel model = (home != null) ? home : new HomeEntity();

        if (data == null)
            return model;

        if (data.getId() != null) model.setID(data.getId());
        if (data.getName() != null) model.setName(data.getName());
        if (data.getBrokerURL() != null) model.setBrokerURL(data.getBrokerURL());
        return model;
    }

    /* ROOM */
    public static RoomModel toRoomModel(RoomData data) {
        return toRoomModel(data, null);
    }

    public static RoomModel toRoomModel(RoomData data, RoomModel room) {
        RoomModel model = (room != null) ? room : new RoomEntity();

        if (data == null)
            return model;

        if (data.getId() != null) model.setID(data.getId());
        if (data.getName() != null) model.setName(data.getName());
        return model;
    }

    /* DEVICE */
    public static DeviceModel toDeviceModel(DeviceInfoData data) {
        return toDeviceModel(data, null);
    }

    public static DeviceModel toDeviceModel(DeviceInfoData data, DeviceModel device) {
        DeviceModel model = (device != null) ? device : new DeviceEntity();

        if (data == null)
            return model;

        if (data.getId() != null) model.setID(data.getId());
        if (data.getName() != null) model.setName(data.getName());
        model.setActive(data.isActive());

        return model;
    }

    /* CAPABILITY */

    public static CapabilityModel toCapabilityModel(CapabilityInfoData data) {
        return toCapabilityModel(data, null);
    }

    public static CapabilityModel toCapabilityModel(CapabilityInfoData data, CapabilityModel cap) {
        CapabilityModel model = (cap != null) ? cap : new CapabilityEntity();

        if (data == null)
            return model;

        if (data.getId() != null) model.setID(data.getId());
        if (data.getName() != null) model.setName(data.getName());
        if (data.getPin() != null) model.setPin(data.getPin());
        if (data.getType() != null) model.setType(data.getType());
        model.setEnabled(data.isEnabled());

        return model;
    }

}

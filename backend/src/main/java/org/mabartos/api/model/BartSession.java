package org.mabartos.api.model;

import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

import java.io.Serializable;

public interface BartSession extends Serializable {

    UserModel getActualUser();

    BartSession setActualUser(Long id);

    HomeModel getActualHome();

    BartSession setActualHome(Long id);

    RoomModel getActualRoom();

    BartSession setActualRoom(Long id);

    DeviceModel getActualDevice();

    BartSession setActualDevice(Long id);

    CapabilityModel getActualCapability();

    BartSession setActualCapability(Long id);

    AppServices services();
}

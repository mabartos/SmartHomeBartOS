package org.mabartos.api.model;

import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeInvitationModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

import java.io.Serializable;
import java.util.UUID;

public interface BartSession extends Serializable {

    void initEnvironment();

    UserModel getActualUser();

    BartSession setActualUser(UUID id);

    HomeModel getActualHome();

    BartSession setActualHome(Long id);

    RoomModel getActualRoom();

    BartSession setActualRoom(Long id);

    DeviceModel getActualDevice();

    BartSession setActualDevice(Long id);

    CapabilityModel getActualCapability();

    BartSession setActualCapability(Long id);

    HomeInvitationModel getActualInvitation();

    BartSession setActualInvitation(Long id);

    MqttClientManager getClientManager();

    AppServices services();

    AuthService auth();
}

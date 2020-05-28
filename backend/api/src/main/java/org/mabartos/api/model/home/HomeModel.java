/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.home;

import org.mabartos.api.common.HasChildren;
import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.mqtt.MqttClientModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.model.user.UserRoleModel;

import java.util.Set;
import java.util.UUID;

public interface HomeModel extends HasChildren<RoomModel>, IdentifiableName<Long> {
    void setName(String name);

    String getBrokerURL();

    void setBrokerURL(String brokerURL);

    /* USERS */
    Set<UserModel> getUsers();

    boolean addUser(UserModel user);

    boolean addUser(UserModel user, UserRole role);

    boolean removeUser(UserModel user);

    /* ROLES */
    Set<UserRoleModel> getUserRoles();

    UserRole getUserRoleByID(UUID userID);

    boolean changeRoleForUser(UserModel user, UserRole role);

    boolean removeRoleForUser(UUID userID);

    /* DEVICES */
    Set<DeviceModel> getUnAssignedDevices();

    boolean addDevice(DeviceModel device);

    boolean removeDeviceFromHome(DeviceModel device);

    void removeAllDevices();

    /* ROOMS */
    void clearRooms();

    /* MQTT */
    MqttClientModel getMqttClient();

    void setMqttClient(MqttClientModel mqttClient);

    /* INVITATIONS */
    Set<HomeInvitationModel> getInvitations();

    boolean addInvitation(HomeInvitationModel invitation);

    boolean removeInvitation(HomeInvitationModel invitation);

    void removeAllInvitations();

    /* COMPUTED */
    Long getMqttClientID();

    Integer getUsersCount();

    boolean isHomeActive();
}

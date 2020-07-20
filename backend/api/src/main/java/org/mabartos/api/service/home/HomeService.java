/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.home;

import org.mabartos.api.controller.user.HomeMemberRoleData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;

import java.util.Set;
import java.util.UUID;

public interface HomeService extends CRUDService<HomeModel, Long>, AbleSendMQTT {

    HomeInvitationService invitations();

    boolean addDeviceToHome(DeviceModel device, Long homeID);

    HomeModel addUserToHome(UUID userID, Long homeID);

    boolean removeDeviceFromHome(DeviceModel device, Long homeID);

    Set<DeviceModel> getAllUnAssignedDevices(Long homeID);

    HomeModel updateFromJson(Long homeID, String JSON);

    Set<UserRoleModel> getAllHomeMembers(Long homeID);

    Set<HomeMemberRoleData> getAllHomeMembersData(Long homeID);
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.home;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.controller.home.HomeResource;
import org.mabartos.api.controller.home.invitations.HomeInvitationsResource;
import org.mabartos.api.controller.home.mqtt.MqttResource;
import org.mabartos.api.controller.room.RoomsResource;
import org.mabartos.api.controller.user.HomeMemberRoleData;
import org.mabartos.api.controller.user.UserRoleData;
import org.mabartos.api.controller.user.UsersResource;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.controller.device.DevicesResourceProvider;
import org.mabartos.controller.home.invitations.HomeInvitationsProvider;
import org.mabartos.controller.home.mqtt.MqttResourceProvider;
import org.mabartos.controller.room.RoomsResourceProvider;
import org.mabartos.controller.user.UsersResourceProvider;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@HasRoleInHome
public class HomeResourceProvider implements HomeResource {

    private final BartSession session;

    public HomeResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    @Path(DevicesResource.PATH)
    public Set<DeviceModel> getDevices() {
        return session.getActualHome().getUnAssignedDevices();
    }

    @GET
    public HomeModel getHome() {
        return session.getActualHome();
    }

    @GET
    @Path("/my-role")
    public UserRoleData getAuthUserRole() {
        UserModel user = session.services().users().findByID(session.auth().getID());
        if (user != null) {
            UserRole role = session.getActualHome().getUserRoleByID(user.getID());
            if (role != null) {
                return new UserRoleData(session.getActualHome().getID(), role);
            }
        }
        return null;
    }

    @GET
    @Path("/members")
    public Set<HomeMemberRoleData> getMembers() {
        return session.services().homes().getAllHomeMembersData(session.getActualHome().getID());
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    public HomeModel updateHome(String JSON) {
        return session.services().homes().updateFromJson(session.getActualHome().getID(), JSON);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    public Response deleteHome() {
        if (session.services().homes().deleteByID(session.getActualHome().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path("/mqtt")
    public MqttResource forwardToMqttInfo() {
        return new MqttResourceProvider(session);
    }

    @Path(DevicesResource.PATH)
    public DevicesResource forwardToDevices() {
        return new DevicesResourceProvider(session);
    }

    @Path(UsersResource.USER_PATH)
    public UsersResource forwardToUsers() {
        return new UsersResourceProvider(session);
    }

    @Path(RoomsResource.ROOM_PATH)
    public RoomsResource forwardToRooms() {
        return new RoomsResourceProvider(session);
    }

    @Override
    public HomeInvitationsResource forwardToInvitations() {
        return new HomeInvitationsProvider(session);
    }
}

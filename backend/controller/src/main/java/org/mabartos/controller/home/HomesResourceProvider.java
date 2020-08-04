/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.home;

import io.quarkus.security.Authenticated;
import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.home.HomeResource;
import org.mabartos.api.controller.home.HomesResource;
import org.mabartos.api.controller.user.UserRoleData;
import org.mabartos.api.data.general.home.HomeData;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.services.utils.DataToModelBase;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Authenticated
@RequestScoped
public class HomesResourceProvider implements HomesResource {
    public static Logger logger = Logger.getLogger(HomesResourceProvider.class.getName());

    private BartSession session;

    @Inject
    public HomesResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Response getAll() {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            return Response.ok(user.getHomes()).build();
        }
        return Response.status(400).build();
    }

    @GET
    @Path("/my-roles")
    public Set<UserRoleData> getMyHomesRoles() {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            return session.services().users().roles().getAllUserRolesData(user.getID());
        }
        return Collections.emptySet();
    }

    @POST
    public HomeModel createHome(String JSON) {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            HomeModel home = DataToModelBase.toHomeModel(HomeData.fromJson(JSON));
            home.addUser(user, UserRole.HOME_ADMIN);
            user.addHome(home);
            return session.services().homes().create(home);
        }
        return null;
    }

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    @Path("/add" + HOME_ID)
    public HomeModel addHomeToUser(@PathParam(HOME_ID_NAME) Long id) {
        return session.services().homes().addUserToHome(session.auth().getID(), id);
    }

    @Path(HOME_ID)
    public HomeResource forwardToHome(@PathParam(HOME_ID_NAME) Long id) {
        if (ControllerUtil.existsItem(session.services().homes(), id)
                && (session.getActualUser() == null || (ControllerUtil.containsItem(session.getActualUser().getHomes(), id)))) {
            return new HomeResourceProvider(session.setActualHome(id));
        }
        return null;
    }
}

package org.mabartos.controller.user;

import org.mabartos.api.controller.home.HomesResource;
import org.mabartos.api.controller.user.UserResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.controller.home.HomesResourceProvider;
import org.mabartos.persistence.model.user.UserModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResourceProvider implements UserResource {

    private final BartSession session;

    public UserResourceProvider(BartSession session) {
        this.session = session;
        this.session.initEnvironment();
    }

    @GET
    public UserModel getUser() {
        return session.getActualUser();
    }

    @PATCH
    public UserModel updateUser(@Valid UserModel user) {
        return session.services().users().updateByID(session.getActualUser().getID(), user);
    }

    @POST
    public UserModel addUserToHome() {
        UserModel user = session.getActualUser();
        if (session.getActualHome() != null) {
            user.addHome(session.getActualHome());
            return session.services().users().updateByID(session.getActualUser().getID(), user);
        }
        return null;
    }

    @DELETE
    public Response deleteUser() {
        if (session.services().users().deleteByID(session.getActualUser().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path(HomesResource.HOME_PATH)
    public HomesResource forwardToHomes() {
        return new HomesResourceProvider(session);
    }
}

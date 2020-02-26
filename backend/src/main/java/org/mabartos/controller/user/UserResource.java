package org.mabartos.controller.user;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.home.HomesResource;
import org.mabartos.persistence.model.UserModel;

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

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResource {

    private final BartSession session;

    public UserResource(BartSession session) {
        this.session = session;
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
            user.addChild(session.getActualHome());
            return session.services().users().updateByID(session.getActualUser().getID(), user);
        }
        return null;
    }

    @DELETE
    public boolean deleteUser() {
        return session.services().users().deleteByID(session.getActualUser().getID());
    }

    @Path(HomesResource.HOME_PATH)
    public HomesResource forwardToHome() {
        return new HomesResource(session);
    }
}

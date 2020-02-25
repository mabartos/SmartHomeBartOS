package org.mabartos.controller.user;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class UsersResource {
    private static final String USER_ID_NAME = "idUser";
    public static final String USER_ID = "/{" + USER_ID_NAME + ":[\\d]+}";
    public static final String USER_PATH = "/users";

    private final BartSession session;

    @Inject
    public UsersResource(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<UserModel> getAll() {
        return (session.getActualHome() != null) ? session.getActualHome().getUsers() : session.users().getAll();
    }

    @GET
    @Path("/search")
    public UserModel getUserByEmail(@QueryParam("email") String email) {
        if (email != null)
            return session.users().findByEmail(email);
        return null;
    }

    @GET
    @Path("/search")
    public UserModel getUserByUsername(@QueryParam("username") String username) {
        if (username != null)
            return session.users().findByUsername(username);
        return null;
    }

    @POST
    public UserModel createUser(@Valid UserModel user) {
        return session.users().create(user);
    }

    @POST
    @Path(USER_ID + "/add")
    public UserModel addUserToHome(@PathParam(USER_ID_NAME) Long id) {
        UserModel user = session.users().findByID(id);
        if (user != null && session.getActualHome() != null) {
            user.addChild(session.getActualHome());
            session.getActualHome().addUser(user);
            return session.users().updateByID(id, user);
        }
        return null;
    }

    @Path(USER_ID)
    public UserResource forwardToUser(@PathParam(USER_ID_NAME) Long id) {
        if (session.getActualHome() == null || ControllerUtil.containsItem(session.getActualHome().getUsers(), id)) {
            return new UserResource(session.setActualUser(id));
        }
        return null;
    }
}

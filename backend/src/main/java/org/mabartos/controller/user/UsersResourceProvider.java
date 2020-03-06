package org.mabartos.controller.user;

import org.mabartos.api.controller.user.UserResource;
import org.mabartos.api.controller.user.UsersResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.protocols.mqtt.utils.TopicUtils;

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
public class UsersResourceProvider implements UsersResource {

    private final BartSession session;

    @Inject
    public UsersResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<UserModel> getAll() {
        return (session.getActualHome() != null) ? session.getActualHome().getUsers() : session.services().users().getAll();
    }

    @GET
    @Path("/test")
    public String asd() {
        TopicUtils.getSpecificTopic("/homes/5/devices/2/temp/3");
        return "asd";
    }

    @GET
    @Path("/search")
    public UserModel getUserByEmail(@QueryParam("email") String email) {
        if (email != null)
            return session.services().users().findByEmail(email);
        return null;
    }

    @GET
    @Path("/search")
    public UserModel getUserByUsername(@QueryParam("username") String username) {
        if (username != null)
            return session.services().users().findByUsername(username);
        return null;
    }

    @POST
    public UserModel createUser(@Valid UserModel user) {
        return session.services().users().create(user);
    }

    @POST
    @Path(USER_ID + "/add")
    public UserModel addUserToHome(@PathParam(USER_ID_NAME) Long id) {
        UserModel user = session.services().users().findByID(id);
        if (user != null && session.getActualHome() != null) {
            user.addChild(session.getActualHome());
            session.getActualHome().addUser(user);
            return session.services().users().updateByID(id, user);
        }
        return null;
    }

    @Path(USER_ID)
    public UserResourceProvider forwardToUser(@PathParam(USER_ID_NAME) Long id) {
        if (session.getActualHome() == null || ControllerUtil.containsItem(session.getActualHome().getUsers(), id)) {
            return new UserResourceProvider(session.setActualUser(id));
        }
        return null;
    }
}

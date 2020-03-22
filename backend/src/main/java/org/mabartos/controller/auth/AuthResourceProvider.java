package org.mabartos.controller.auth;

import org.mabartos.api.controller.auth.AuthResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.api.service.auth.AuthData;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class AuthResourceProvider implements AuthResource {

    private BartSession session;

    @Inject
    public AuthResourceProvider(BartSession session) {
        this.session = session;
    }

    @Override
    @Path("/login")
    @POST
    public Response login(AuthData authData) {
        UserModel found = session.services().auth().login(authData);
        return found != null ? Response.ok(found).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}

package org.mabartos.controller.auth;

import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import org.mabartos.api.controller.auth.AuthResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.api.service.auth.AuthData;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class AuthResourceProvider implements AuthResource {

    private BartSession session;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    public AuthResourceProvider(BartSession session) {
        this.session = session;
    }

    @Path("/test")
    @GET
    public Response get() {
        Set<Credential> cred = securityIdentity.getCredentials();
        System.out.println("AUTH");
        cred.forEach(System.out::println);
        Set<String> sd = securityIdentity.getRoles();
        sd.forEach(System.out::println);
        return Response.ok(securityIdentity.toString()).build();
    }

    @Override
    @Path("/login")
    @POST
    public Response login(AuthData authData) {
        return null;
        /*UserModel found = session.services().auth().login(authData);
        return found != null ? Response.ok(found).build() : Response.status(Response.Status.BAD_REQUEST).build();*/
    }
}

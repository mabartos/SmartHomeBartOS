/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.user;

import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.user.UserResource;
import org.mabartos.api.model.user.UserModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResourceProvider implements UserResource {

    private final BartSession session;

    public UserResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public UserModel getUser() {
        return session.getActualUser();
    }

    @POST
    public UserModel addUserToHome() {
        UserModel user = session.getActualUser();
        if (session.getActualHome() != null) {
            user.addHome(session.getActualHome());
            session.getActualHome().addUser(user);
            return session.services().users().updateByID(session.getActualUser().getID(), user);
        }
        return null;
    }
}

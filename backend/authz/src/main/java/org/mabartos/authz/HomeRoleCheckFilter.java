/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.authz;

import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.home.HomesResource;
import org.mabartos.api.controller.room.RoomsResource;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.service.AppServices;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.UUID;

@HasRoleInHome
@Provider
@Authenticated
@Priority(Priorities.AUTHENTICATION)
public class HomeRoleCheckFilter implements ContainerRequestFilter {


    @Inject
    AppServices services;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {

        HasRoleInHome roleAnnotations = resourceInfo.getResourceClass().getAnnotation(HasRoleInHome.class);
        HasRoleInHome methodRole = resourceInfo.getResourceMethod().getAnnotation(HasRoleInHome.class);

        if (methodRole != null) {
            roleAnnotations = methodRole;
        }

        Principal basicPrincipal = containerRequestContext.getSecurityContext().getUserPrincipal();
        if (!(basicPrincipal instanceof DefaultJWTCallerPrincipal) || roleAnnotations == null) {
            abortWithUnauthorized(containerRequestContext);
            return;
        }

        DefaultJWTCallerPrincipal principal = (DefaultJWTCallerPrincipal) basicPrincipal;
        UUID userID = UUID.fromString(principal.getSubject());
        Long homeID = getPathID(HomesResource.HOME_ID_NAME, containerRequestContext);

        if (homeID != null) {
            HomeModel home = services.homes().findByID(homeID);
            if (home == null || home.getUserRoleByID(userID) == null) {
                abortWithUnauthorized(containerRequestContext);
                return;
            }

            UserRole role = home.getUserRoleByID(userID);

            UserRole[] roles = roleAnnotations.roles();

            boolean hasRequiredRole;
            if (roles.length > 0) {
                hasRequiredRole = Arrays.asList(roleAnnotations.roles()).contains(role);
            } else {
                hasRequiredRole = role.moreValuableOrEqualThan(roleAnnotations.minRole());
            }

            if (!hasRequiredRole && !checkIfOwner(roleAnnotations, containerRequestContext, userID)) {
                abortWithUnauthorized(containerRequestContext);
                return;
            }
        } else {
            containerRequestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext context) {
        context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private boolean checkIfOwner(HasRoleInHome annotation, ContainerRequestContext context, UUID userID) {
        if (annotation.orIsOwner()) {
            Long roomID = getPathID(RoomsResource.ROOM_ID_NAME, context);
            if (roomID != null) {
                RoomModel room = services.rooms().findByID(roomID);
                return room != null && room.isOwner(userID);
            }
        }
        return false;
    }

    private Long getPathID(String idName, ContainerRequestContext context) {
        if (idName != null && context != null) {
            String value = context.getUriInfo().getPathParameters().getFirst(idName);
            return (value != null) ? Long.parseLong(value) : null;
        }
        return null;
    }
}

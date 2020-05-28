/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.user;

import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.home.HomeModel;

import java.util.Set;
import java.util.UUID;

public interface UserModel extends IdentifiableName<UUID> {

    void setUsername(String username);

    String getEmail();

    void setEmail(String email);

    Set<HomeModel> getHomes();

    boolean addHome(HomeModel child);

    boolean removeHome(HomeModel child);

    boolean removeHomeByID(Long id);

    /* INVITATIONS */

    Set<HomeInvitationModel> getInvitations();

    HomeInvitationModel getInvitationByID(Long id);

    boolean addInvitation(HomeInvitationModel invitation);

    boolean removeInvitation(HomeInvitationModel invitation);

    void removeAllInvitations();

    /* ROLES */
    Set<UserRoleModel> getRoles();

    void setRoles(Set<UserRoleModel> roles);
}

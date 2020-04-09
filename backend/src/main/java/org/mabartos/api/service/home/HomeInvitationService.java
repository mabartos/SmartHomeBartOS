package org.mabartos.api.service.home;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.home.HomeInvitationModel;
import org.mabartos.persistence.model.user.UserModel;

import java.util.Set;
import java.util.UUID;

public interface HomeInvitationService extends CRUDService<HomeInvitationModel, Long> {

    Set<HomeInvitationModel> getHomesInvitations(Long homeID);

    Set<HomeInvitationModel> getUsersInvitations(UUID userID);

    HomeInvitationModel createFromJSON(UserModel issuer, String JSON);

    HomeInvitationModel updateFromJSON(Long invitationID, String JSON);

    int deleteAllFromHome(Long homeID);

    HomeInvitationModel getValidUserInvitation(Long invitationID, UserModel user);

    boolean acceptInvitation(Long invitationID, UserModel user);

    boolean dismissInvitation(Long invitationID, UserModel user);
}


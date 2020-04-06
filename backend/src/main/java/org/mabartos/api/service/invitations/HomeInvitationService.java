package org.mabartos.api.service.invitations;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.HomeInvitationModel;

import java.util.Set;
import java.util.UUID;

public interface HomeInvitationService extends CRUDService<HomeInvitationModel, Long> {

    Set<HomeInvitationModel> getHomesInvitations(Long homeID);

    Set<HomeInvitationModel> getUsersInvitations(UUID userID);

    HomeInvitationModel updateFromJSON(Long invitationID, String JSON);
}


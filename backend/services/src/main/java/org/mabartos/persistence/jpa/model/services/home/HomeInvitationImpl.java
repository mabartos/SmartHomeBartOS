/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.home;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.data.general.home.invitations.HomeInvitationData;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.home.HomeInvitationConflictException;
import org.mabartos.api.service.home.HomeInvitationService;
import org.mabartos.persistence.jpa.repository.HomeInvitationRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Dependent
public class HomeInvitationImpl extends CRUDServiceImpl<HomeInvitationModel, HomeInvitationEntity, HomeInvitationRepository, Long> implements HomeInvitationService {

    private AppServices services;

    public void start(@Observes StartupEvent event) {
    }

    @Inject
    public HomeInvitationImpl(HomeInvitationRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    @Override
    public Set<HomeInvitationModel> getHomesInvitations(Long homeID) {
        Query query = entityManager.createNamedQuery("getHomesInvitations");
        query.setParameter("homeID", homeID);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<HomeInvitationModel> getUsersInvitations(UUID userID) {
        Query query = entityManager.createNamedQuery("getUsersInvitations");
        query.setParameter("userID", userID);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public HomeInvitationModel createFromJSON(UserModel issuer, String JSON) throws HomeInvitationConflictException {
        HomeInvitationData data = HomeInvitationData.fromJSON(JSON);

        if (data != null && issuer != null) {
            UserModel receiver = services.users().findByID(data.getReceiverID());
            HomeModel home = services.homes().findByID(data.getHomeID());

            if (receiver != null && home != null) {
                HomeInvitationModel invitation = new HomeInvitationEntity(receiver, home);
                invitation.setIssuerID(issuer.getID());

                boolean isUnique = getUsersInvitations(issuer.getID())
                        .stream()
                        .noneMatch(f -> f.equalsWithoutID(invitation));

                if (!isUnique) {
                    throw new HomeInvitationConflictException();
                }
                return services.homes().invitations().create(invitation);
            }
        }
        return null;
    }

    @Override
    public HomeInvitationModel updateFromJSON(Long invitationID, String JSON) {
        HomeInvitationModel model = getRepository().findById(invitationID);
        HomeInvitationData data = HomeInvitationData.fromJSON(JSON);
        if (model != null && data != null) {
            model.setIssuerID(data.getIssuerID());
            UserModel receiver = services.users().findByID(data.getReceiverID());
            HomeModel home = services.homes().findByID(data.getHomeID());
            if (receiver != null && home != null) {
                model.setReceiver(receiver);
                model.setHome(home);
                services.homes().invitations().updateByID(invitationID, model);
            }
        }
        return null;
    }

    @Override
    public boolean acceptInvitation(Long invitationID, UserModel authUser) {
        HomeInvitationModel invitation = getValidUserInvitation(invitationID, authUser);
        UserModel user = services.users().findByID(authUser.getID());
        if (invitation != null) {
            HomeModel home = services.homes().findByID(invitation.getHomeID());
            if (home != null) {
                user.addHome(home);
                home.addUser(user);
                services.homes().invitations().deleteByID(invitationID);
                return services.users().updateByID(user.getID(), user) != null;
            }
        }
        return false;
    }

    @Override
    public HomeInvitationModel getValidUserInvitation(Long invitationID, UserModel user) {
        if (user != null) {
            HomeInvitationModel invitation = user.getInvitationByID(invitationID);
            if (invitation != null && invitation.getReceiver().equals(user)) {
                return invitation;
            }
        }
        return null;
    }

    @Override
    public boolean dismissInvitation(Long invitationID, UserModel authUser) {
        UserModel user = services.users().findByID(authUser.getID());
        if (getValidUserInvitation(invitationID, user) != null) {
            return services.homes().invitations().deleteByID(invitationID);
        }
        return false;
    }

    @Override
    public int deleteAllFromHome(Long homeID) {
        getEntityManager().flush();
        getEntityManager().clear();

        Query query = entityManager.createNamedQuery("deleteHomeInvitations");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }
}

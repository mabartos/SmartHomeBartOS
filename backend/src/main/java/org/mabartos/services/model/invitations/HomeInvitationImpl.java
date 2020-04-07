package org.mabartos.services.model.invitations;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.invitations.HomeInvitationConflictException;
import org.mabartos.api.service.invitations.HomeInvitationService;
import org.mabartos.controller.data.HomeInvitationData;
import org.mabartos.persistence.model.HomeInvitationModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.persistence.repository.HomeInvitationRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Dependent
public class HomeInvitationImpl extends CRUDServiceImpl<HomeInvitationModel, HomeInvitationRepository, Long> implements HomeInvitationService {

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
                HomeInvitationModel invitation = new HomeInvitationModel(receiver, home);
                invitation.setIssuerID(issuer.getID());

                boolean isUnique = getUsersInvitations(issuer.getID())
                        .stream()
                        .noneMatch(f -> f.equalsWithoutID(invitation));

                if (!isUnique) {
                    throw new HomeInvitationConflictException();
                }
                return services.invitations().create(invitation);
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
                services.invitations().updateByID(invitationID, model);
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
                services.invitations().deleteByID(invitationID);
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
            return services.invitations().deleteByID(invitationID);
        }
        return false;
    }

    @Override
    public int deleteAllFromHome(Long homeID) {
        Query query = entityManager.createNamedQuery("deleteHomeInvitations");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }
}

package org.mabartos.services.model.invitations;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.invitations.HomeInvitationService;
import org.mabartos.controller.data.HomeInvitationData;
import org.mabartos.persistence.model.HomeInvitationModel;
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
    public HomeInvitationModel updateFromJSON(Long invitationID, String JSON) {
        HomeInvitationModel model = getRepository().findById(invitationID);
        HomeInvitationData data = HomeInvitationData.fromJSON(JSON);
        if (model != null && data != null) {
            model.setIssuerID(data.getIssuerID());
            model.setReceiver(services.users().findByID(data.getReceiverID()));
            model.setHome(services.homes().findByID(data.getHomeID()));
            services.invitations().updateByID(invitationID, model);
        }
        return null;
    }
}

package org.mabartos.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.home.HomeInvitationModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HomeInvitationRepository implements PanacheRepository<HomeInvitationModel> {
}

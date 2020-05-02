package org.mabartos.persistence.jpa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.services.model.home.HomeInvitationEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HomeInvitationRepository implements PanacheRepository<HomeInvitationEntity> {
}

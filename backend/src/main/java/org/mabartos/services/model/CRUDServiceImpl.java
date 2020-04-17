package org.mabartos.services.model;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.api.service.CRUDService;
import org.mabartos.interfaces.Identifiable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public class CRUDServiceImpl
        <Model extends Identifiable<ID>, Repo extends PanacheRepository<Model>, ID> implements CRUDService<Model, ID> {

    private Repo repository;

    @PersistenceContext
    protected EntityManager entityManager;

    @Inject
    public CRUDServiceImpl(Repo repository) {
        this.repository = repository;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Repo getRepository() {
        return this.repository;
    }

    @Override
    public Model create(Model entity) {
        try {
            if (!repository.isPersistent(entity)) {
                repository.persist(entity);
                repository.flush();
            }
            return entity;
        } catch (Exception e) {
            // HIBERNATE BUG
            e.printStackTrace();
            return entity;
        }
    }

    @Override
    public Model updateByID(ID id, Model entity) {
        Model found = findByID(id);
        if (entity != null && found != null) {
            entity.setID(id);
            entityManager.merge(entity);
            entityManager.flush();
            return entity;
        }
        return null;
    }

    @Override
    public Set<Model> getAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Model findByID(ID id) {
        try {
            return repository.find("id", id).firstResult();
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public boolean deleteByID(ID id) {
        return repository.delete("id", id) > 0;
    }

}

package org.mabartos.services.core;

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
        <T extends Identifiable, Repo extends PanacheRepository<T>> implements CRUDService<T> {

    private Repo repository;

    @PersistenceContext
    protected EntityManager entityManager;

    @Inject
    CRUDServiceImpl(Repo repository) {
        this.repository = repository;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Repo getRepository() {
        return this.repository;
    }

    @Override
    public T create(T entity) {
        try {
            if (!repository.isPersistent(entity)) {
                repository.persist(entity);
                repository.flush();
            }
            return entity;
        } catch (Exception e) {
            // HIBERNATE BUG
            return entity;
        }
    }

    @Override
    public T updateByID(Long id, T entity) {
        T found = findByID(id);
        if (entity != null && found != null) {
            entity.setID(id);
            entityManager.merge(entity);
            entityManager.flush();
            return entity;
        }
        return null;
    }

    @Override
    public Set<T> getAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public T findByID(Long id) {
        try {
            return repository.find("id", id).firstResult();
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public boolean deleteByID(Long id) {
        return repository.delete("id", id) > 0;
    }

}

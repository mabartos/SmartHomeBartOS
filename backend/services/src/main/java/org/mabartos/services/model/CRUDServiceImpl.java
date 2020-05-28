/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.model;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.api.common.Identifiable;
import org.mabartos.api.service.CRUDService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@SuppressWarnings("unchecked")
public class CRUDServiceImpl
        <Model extends Identifiable<ID>, Entity extends Model, Repo extends PanacheRepository<Entity>, ID> implements CRUDService<Model, ID> {

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
            if (!repository.isPersistent((Entity) entity)) {
                repository.persist((Entity) entity);
                repository.flush();
            }
            return entity;
        } catch (Exception e) {
            // HIBERNATE BUG
            e.printStackTrace();
        }
        return null;
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

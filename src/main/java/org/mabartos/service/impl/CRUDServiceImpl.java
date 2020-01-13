package org.mabartos.service.impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.service.core.CRUDService;
import org.mabartos.utils.Identifiable;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;

public class CRUDServiceImpl<T extends Identifiable, Repo extends PanacheRepository<T>> implements CRUDService<T> {

    private Repo repository;

    @Inject
    CRUDServiceImpl(Repo repository) {
        this.repository = repository;
    }

    public Repo getRepository() {
        return this.repository;
    }

    @Override
    public T create(T entity) {
        repository.persist(entity);
        return repository.findById(entity.getID());
    }

    @Override
    public T updateByID(Long id, T entity) {
        T found = repository.findById(id);
        if (entity != null && found != null) {
            entity.setID(id);
            repository.persist(entity);
            return repository.findById(id);
        }
        return null;
    }

    @Override
    public T findByID(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll().list();
    }

    @Override
    public T getByID(Long id) {
        return repository
                .findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean deleteByID(Long id) {
        return repository.delete("id", id) > 0;
    }
}

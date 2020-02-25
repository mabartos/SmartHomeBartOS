package org.mabartos.api.service;

import java.util.Set;

public interface CRUDService<T> {

    T create(T entity);

    T updateByID(Long id, T entity);

    Set<T> getAll();

    T findByID(Long id);

    boolean deleteByID(Long id);
}

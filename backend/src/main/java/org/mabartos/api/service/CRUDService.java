package org.mabartos.api.service;

import java.util.Set;

public interface CRUDService<T, ID> {

    T create(T entity);

    T updateByID(ID id, T entity);

    Set<T> getAll();

    T findByID(ID id);

    boolean deleteByID(ID id);
}

package org.mabartos.service.core;

import java.util.List;

public interface CRUDService<T> {

    T create(T entity);

    T updateByID(Long id, T entity);

    T findByID(Long id);

    List<T> getAll();

    T getByID(Long id);

    boolean deleteByID(Long id);
}

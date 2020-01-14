package org.mabartos.service.core;

import java.util.List;

public interface CRUDService<T> {

    T create(T entity);

    T updateByID(Long id, T entity);
    
    List<T> getAll();

    T findByID(Long id);

    boolean deleteByID(Long id);
}

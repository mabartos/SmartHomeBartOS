package service;

import utils.Identifiable;

import java.util.Set;

public interface CRUDService<T extends Identifiable> {

    T create(T entity);

    T updateByID(Long id, T entity);

    Set<T> getAll();

    T findByID(Long id);

    boolean exists(Long id);

    boolean deleteByID(Long id);
}

package org.mabartos.utils;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.io.Serializable;
import java.util.List;

public interface HasChildren<T> extends Serializable, Identifiable {

    List<T> getChildren();

    boolean addChild(T child);

    boolean removeChild(T child);

    boolean removeChildByID(Long id);
}

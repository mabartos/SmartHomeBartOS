package org.mabartos.interfaces;

import java.io.Serializable;
import java.util.Set;

public interface HasChildren<T> extends Serializable, Identifiable {

    Set<T> getChildren();

    boolean addChild(T child);

    boolean removeChild(T child);

    boolean removeChildByID(Long id);
}

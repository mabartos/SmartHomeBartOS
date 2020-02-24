package org.mabartos.api.service;

import org.mabartos.interfaces.HasChildren;
import org.mabartos.interfaces.Identifiable;

public interface CRUDServiceChild<T extends Identifiable, Parent extends HasChildren<T>> extends CRUDService<T> {

    boolean setParentModel(Parent parent);

    boolean addModelToParent(Long id);

    boolean isParentSet();
}

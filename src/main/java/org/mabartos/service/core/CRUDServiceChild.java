package org.mabartos.service.core;

import org.mabartos.utils.HasChildren;
import org.mabartos.utils.Identifiable;

public interface CRUDServiceChild<T extends Identifiable, Parent extends HasChildren<T>> extends CRUDService<T> {

    boolean setParentModel(Parent parent);

    boolean addModelToParent(Long id);

    boolean isParentSet();
}

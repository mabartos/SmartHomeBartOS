package service;

import utils.HasChildren;
import utils.Identifiable;

public interface CRUDServiceChild<T extends Identifiable, Parent extends HasChildren<T>> extends CRUDService<T> {

    boolean setParentModel(Parent parent);

    boolean addModelToParent(Long id);

    boolean isParentSet();
}

package org.mabartos.service.impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.service.core.CRUDServiceChild;
import org.mabartos.service.core.UserService;
import org.mabartos.utils.HasChildren;
import org.mabartos.utils.Identifiable;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CRUDServiceChildImpl
        <Model extends Identifiable, Repo extends PanacheRepository<Model>, Parent extends HasChildren<Model>>
        extends CRUDServiceImpl<Model, Repo>
        implements CRUDServiceChild<Model, Parent> {

    private Parent parentModel;
    private UserService userService;

    CRUDServiceChildImpl(Repo repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public boolean setParentModel(Parent parent) {
        if (parent != null) {
            this.parentModel = parent;
            return true;
        }
        return false;
    }

    //TODO update userModel
    @Override
    public boolean addModelToParent(Long id) {
        if (parentModel != null && id != null) {
            Model found = super.findByID(id);
            if (found != null && parentModel.addChild(found)) {
                userService.updateByID(parentModel.getID(), (UserModel) parentModel);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isParentSet() {
        return parentModel != null;
    }

    private boolean isBadID(Long id) {
        return isParentSet() && parentModel.getChildren()
                .stream()
                .noneMatch(f -> f.getID().equals(id));
    }

    @Override
    public Model create(Model entity) {
        Model created = super.create(entity);
        if (created != null) {
            parentModel.addChild(created);
            return created;
        }
        return null;
    }

    @Override
    public Model updateByID(Long id, Model entity) {
        if (isBadID(id))
            return null;

        return super.updateByID(id, entity);
    }

    @Override
    public List<Model> getAll() {
        if (isParentSet())
            return parentModel.getChildren();

        return super.getAll();
    }

    @Override
    public Model findByID(Long id) {
        if (isBadID(id))
            return null;

        return super.findByID(id);
    }

    @Override
    public boolean deleteByID(Long id) {
        if (isBadID(id))
            return false;

        return super.deleteByID(id);
    }
}

package org.mabartos.services.core;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.interfaces.HasChildren;
import org.mabartos.interfaces.Identifiable;
import org.mabartos.api.service.CRUDServiceChild;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
public class CRUDServiceChildImpl
        <Model extends Identifiable, Repo extends PanacheRepository<Model>, Parent extends HasChildren<Model>>
        extends CRUDServiceImpl<Model, Repo>
        implements CRUDServiceChild<Model, Parent> {

    private Parent parentModel;

    CRUDServiceChildImpl(Repo repository) {
        super(repository);
    }

    @Override
    public boolean setParentModel(Parent parent) {
        if (parent != null) {
            this.parentModel = parent;
            return true;
        }
        return false;
    }

    // TODO only if HOME_ADMIN
    @Override
    @SuppressWarnings("unchecked")
    public boolean addModelToParent(Long id) {
        if (parentModel != null && id != null) {
            Model found = super.findByID(id);
            if (found != null && parentModel.addChild(found)) {
                getEntityManager().merge(parentModel);
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
            if (parentModel != null) {
                parentModel.addChild(created);
                getEntityManager().merge(parentModel);
                getEntityManager().flush();
            }
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
    public Set<Model> getAll() {
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

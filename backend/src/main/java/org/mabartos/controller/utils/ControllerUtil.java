package org.mabartos.controller.utils;

import org.mabartos.api.service.CRUDService;
import org.mabartos.interfaces.Identifiable;

import java.util.Set;

public class ControllerUtil {

    public static <Child extends Identifiable<ID>, ID> boolean containsItem(Set<Child> parentSet, Child child) {
        return parentSet.stream().anyMatch(f -> f.equals(child));
    }

    public static <Child extends Identifiable<ID>, ID> boolean containsItem(Set<Child> parentSet, ID id) {
        return parentSet.stream().anyMatch(f -> f.getID().equals(id));
    }

    public static <Service extends CRUDService<?, ID>, ID> boolean existsItem(Service service, ID id) {
        return (id != null && service != null && service.findByID(id) != null);
    }
}

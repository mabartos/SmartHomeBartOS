/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.utils;

import org.mabartos.api.common.Identifiable;
import org.mabartos.api.service.CRUDService;

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

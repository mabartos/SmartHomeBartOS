package org.mabartos.controller.utils;

import org.mabartos.interfaces.HasChildren;
import org.mabartos.interfaces.Identifiable;

import java.util.Set;

public class ControllerUtil {

    public static <Child extends Identifiable> boolean containsItem(Set<Child> parentSet, Child child) {
        return parentSet.stream().anyMatch(f -> f.equals(child));
    }

    public static <Child extends Identifiable> boolean containsItem(Set<Child> parentSet, Long id) {
        return parentSet.stream().anyMatch(f -> f.getID().equals(id));
    }
}

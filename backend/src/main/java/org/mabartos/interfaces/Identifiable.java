package org.mabartos.interfaces;

public interface Identifiable<ID> {

    String getName();

    ID getID();

    void setID(ID id);
}

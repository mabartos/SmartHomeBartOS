package org.mabartos.interfaces;

import java.io.Serializable;

public interface Identifiable<ID> extends Serializable {
    
    ID getID();

    void setID(ID id);
}

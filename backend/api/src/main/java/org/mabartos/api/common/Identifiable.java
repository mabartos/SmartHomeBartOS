/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.common;

import java.io.Serializable;

public interface Identifiable<ID> extends Serializable {

    ID getID();

    void setID(ID id);
}

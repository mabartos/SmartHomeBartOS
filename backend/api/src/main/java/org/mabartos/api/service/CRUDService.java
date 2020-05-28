/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service;

import java.util.Set;

public interface CRUDService<Model, ID> {

    Model create(Model entity);

    Model updateByID(ID id, Model entity);

    Set<Model> getAll();

    Model findByID(ID id);

    boolean deleteByID(ID id);
}

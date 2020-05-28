/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.common;

import java.io.Serializable;
import java.util.Set;

public interface HasChildren<T> extends Serializable {

    Set<T> getChildren();

    boolean addChild(T child);

    boolean removeChild(T child);

    boolean removeChildByID(Long id);
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general;

public interface SerializableJSON {
    default String toJson() {
        return new SerializeUtils(this).toJson();
    }
}

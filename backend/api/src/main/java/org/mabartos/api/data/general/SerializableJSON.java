package org.mabartos.api.data.general;

public interface SerializableJSON {
    default String toJson() {
        return new SerializeUtils(this).toJson();
    }
}

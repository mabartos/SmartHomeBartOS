package org.mabartos.api.model.room;

import org.mabartos.api.common.HasChildren;
import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.common.RoomType;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;

import java.util.Set;
import java.util.UUID;

public interface RoomModel extends HasChildren<DeviceModel>, IdentifiableName<Long> {

    void setName(String name);

    RoomType getType();

    void setType(RoomType type);

    /* Owners */
    Set<UUID> getOwnersID();

    boolean addOwnerID(UUID id);

    boolean removeOwnerID(UUID id);

    boolean isOwner(UUID id);

    /* HOME */
    HomeModel getHome();

    Long getHomeID();

    void setHome(HomeModel home);

    /* COMPUTED */
    Integer getDevicesCount();
}

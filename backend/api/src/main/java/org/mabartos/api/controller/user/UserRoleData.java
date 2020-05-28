/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.data.general.SerializableJSON;

public class UserRoleData implements SerializableJSON {

    @JsonProperty("id")
    private Long homeID;

    @JsonProperty("role")
    private UserRole role;

    @JsonCreator
    public UserRoleData(@JsonProperty("id") Long homeID,
                        @JsonProperty("role") UserRole role) {
        this.role = role;
        this.homeID = homeID;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }
}

/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.user.UserModel;

@JsonPropertyOrder({"username", "homeID", "role"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeMemberRoleData extends UserRoleData {

    @JsonProperty("user")
    private UserModel user;

    public HomeMemberRoleData(@JsonProperty("homeID") Long homeID,
                              @JsonProperty("role") UserRole role,
                              @JsonProperty("user") UserModel user) {
        super(homeID, role);
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}

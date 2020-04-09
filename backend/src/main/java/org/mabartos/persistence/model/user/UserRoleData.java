package org.mabartos.persistence.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.UserRole;
import org.mabartos.protocols.mqtt.data.MqttSerializable;

public class UserRoleData implements MqttSerializable {

    @JsonProperty("role")
    private UserRole role;

    @JsonCreator
    public UserRoleData(@JsonProperty("role") UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

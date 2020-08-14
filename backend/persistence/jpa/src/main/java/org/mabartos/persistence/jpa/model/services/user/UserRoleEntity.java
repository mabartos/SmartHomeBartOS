/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.persistence.jpa.model.services.home.HomeEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "UserRoles")
@Cacheable
@NamedQueries({
        @NamedQuery(name = "deleteAllRolesFromHome", query = "delete from UserRoleEntity where home.id=:homeID"),
        @NamedQuery(name = "getAllUserRoleByUUID", query = "select roles from UserRoleEntity roles join fetch roles.user join fetch roles.home where roles.user.uuid=:userID"),
        @NamedQuery(name = "getAllHomeMembers", query = "select roles from UserRoleEntity roles join fetch roles.user join fetch roles.home where roles.home.id=:homeID")
})
public class UserRoleEntity extends PanacheEntityBase implements UserRoleModel {

    @Id
    @GeneratedValue
    @Column(name = "USER_ROLE_ID")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_ID")
    private HomeEntity home;

    @Enumerated
    @Column(nullable = false)
    private UserRole role = UserRole.HOME_MEMBER;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UserEntity user, HomeEntity home, UserRole role) {
        this.user = user;
        this.home = home;
        this.role = role;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = (UserEntity) user;
    }

    @JsonIgnore
    public HomeEntity getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = (HomeEntity) home;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    /* COMPUTED */
    @JsonProperty(JsonPropertyNames.USER_ID)
    public UUID getUserID() {
        return (user != null) ? user.getID() : null;
    }

    @JsonProperty(JsonPropertyNames.HOME_ID)
    public Long getHomeID() {
        return Optional.ofNullable(home.getID()).orElse(-1L);
    }

    /* MANAGE */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof UserRoleEntity)) {
            return false;
        } else {
            UserRoleEntity model = (UserRoleEntity) obj;

            return (this.getID().equals(model.getID())
                    && this.getHome().equals(model.getHome())
                    && this.getUser().equals(model.getUser())
                    && this.getRole().equals(model.getRole()));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, home, user, role);
    }
}

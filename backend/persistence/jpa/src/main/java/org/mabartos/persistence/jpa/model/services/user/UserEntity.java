/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.persistence.jpa.model.services.home.HomeEntity;
import org.mabartos.persistence.jpa.model.services.home.HomeInvitationEntity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Cacheable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "email"})
@NamedQueries({
        @NamedQuery(name = "findUserByUUID", query = "select user from UserEntity user where user.uuid = :id"),
        @NamedQuery(name = "findUserByNameOrEmail", query = "select user from UserEntity user where lower(user.username) like concat(:name,'%') or user.email like concat(:name,'%')")
})
public class UserEntity extends PanacheEntityBase implements UserModel {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = HomeEntity.class)
    @JoinTable(name = "USERS_HOMES",
            joinColumns = {
                    @JoinColumn(referencedColumnName = "USER_ID")},
            inverseJoinColumns = {
                    @JoinColumn(referencedColumnName = "HOME_ID")}
    )
    private Set<HomeModel> homesSet = new HashSet<>();

    @OneToMany(targetEntity = HomeInvitationEntity.class, mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<HomeInvitationModel> invitations = new HashSet<>();

    @OneToMany(targetEntity = UserRoleEntity.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleModel> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(UUID id, String username) {
        this(username);
        this.uuid = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public UUID getID() {
        return uuid;
    }

    @Override
    public void setID(UUID id) {
        this.uuid = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /* HOMES */
    @JsonIgnore
    public Set<HomeModel> getHomes() {
        return homesSet;
    }

    public boolean addHome(HomeModel child) {
        return homesSet.add(child);
    }

    public boolean removeHome(HomeModel child) {
        return homesSet.remove(child);
    }

    public boolean removeHomeByID(Long id) {
        return homesSet.removeIf(home -> home.getID().equals(id));
    }

    /* INVITATIONS */
    public Set<HomeInvitationModel> getInvitations() {
        return invitations;
    }

    public HomeInvitationModel getInvitationByID(Long id) {
        if (invitations != null) {
            return invitations.stream()
                    .filter(f -> f.getID().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean addInvitation(HomeInvitationModel invitation) {
        return invitations.add(invitation);
    }

    public boolean removeInvitation(HomeInvitationModel invitation) {
        return invitations.remove(invitation);
    }

    public void removeAllInvitations() {
        invitations = Collections.emptySet();
    }

    /* ROLES */
    @JsonIgnore
    public Set<UserRoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleModel> roles) {
        this.roles = roles;
    }

    /* MANAGE */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof UserModel))
            return false;
        else {
            UserModel object = (UserModel) obj;
            return (object.getID().equals(this.getID())
                    && object.getName().equals(this.getName())
                    && object.getEmail().equals(this.getEmail())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username, email);
    }
}

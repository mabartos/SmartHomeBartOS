/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.home;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.persistence.jpa.model.services.user.UserEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "HomeInvitation")
@Cacheable
@JsonPropertyOrder({JsonPropertyNames.ID, JsonPropertyNames.HOME_ID, "homeName", JsonPropertyNames.RECEIVER_ID, JsonPropertyNames.ISSUER_ID})
@NamedQueries({
        @NamedQuery(name = "getHomesInvitations", query = "select inv from HomeInvitationEntity inv join fetch inv.receiver join fetch inv.home where inv.home.id=:homeID"),
        @NamedQuery(name = "getUsersInvitations", query = "select inv from HomeInvitationEntity inv join fetch inv.receiver join fetch inv.home where inv.issuerID=:userID"),
        @NamedQuery(name = "deleteHomeInvitations", query = "delete from HomeInvitationEntity where home.id=:homeID")
})
public class HomeInvitationEntity extends PanacheEntityBase implements HomeInvitationModel {

    @Id
    @GeneratedValue
    @Column(name = "INVITATION_ID")
    Long id;

    @Column
    private UUID issuerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_ID")
    private HomeEntity home;

    public HomeInvitationEntity() {
    }

    public HomeInvitationEntity(UserModel receiver, HomeModel home) {
        this.receiver = (UserEntity) receiver;
        this.home = (HomeEntity) home;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public UUID getIssuerID() {
        return issuerID;
    }

    public void setIssuerID(UUID issuerID) {
        this.issuerID = issuerID;
    }

    @JsonIgnore
    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = (UserEntity) receiver;
    }

    @JsonIgnore
    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = (HomeEntity) home;
    }

    // Computed

    @JsonProperty(JsonPropertyNames.RECEIVER_ID)
    public UUID getReceiverID() {
        return (receiver != null) ? receiver.getID() : null;
    }

    @JsonProperty(JsonPropertyNames.HOME_ID)
    public Long getHomeID() {
        return Optional.ofNullable(home.getID()).orElse(-1L);
    }

    @JsonProperty("homeName")
    public String getHomeName() {
        return Optional.ofNullable(home.getName()).orElse("");
    }

    public boolean equalsWithoutID(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof HomeInvitationEntity)) {
            return false;
        } else {
            HomeInvitationEntity object = (HomeInvitationEntity) obj;

            return (this.getIssuerID().equals(object.getIssuerID())
                    && this.getReceiver().equals(object.getReceiver())
                    && this.getHome().equals(object.getHome())
            );
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (equalsWithoutID(obj)) {
            HomeInvitationEntity object = (HomeInvitationEntity) obj;
            return (this.getID().equals(object.getID()));
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, issuerID, receiver, home);
    }
}

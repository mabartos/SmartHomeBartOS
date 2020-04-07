package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.interfaces.Identifiable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "HomeInvitation")
@Cacheable
@JsonPropertyOrder({"id", "homeID", "homeName", "receiverID", "issuerID"})
@NamedQueries({
        @NamedQuery(name = "getHomesInvitations", query = "select inv from HomeInvitationModel inv where inv.home.id=:homeID"),
        @NamedQuery(name = "getUsersInvitations", query = "select inv from HomeInvitationModel inv where inv.issuerID=:userID"),
        @NamedQuery(name = "deleteHomeInvitations", query = "delete from HomeInvitationModel where home.id=:homeID")
})
public class HomeInvitationModel extends PanacheEntityBase implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "INVITATION_ID")
    Long id;

    @Column
    private UUID issuerID;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserModel receiver;

    @ManyToOne
    @JoinColumn(name = "HOME_ID")
    private HomeModel home;

    public HomeInvitationModel() {
    }

    public HomeInvitationModel(UserModel receiver, HomeModel home) {
        this.receiver = receiver;
        this.home = home;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return null;
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
        this.receiver = receiver;
    }

    @JsonIgnore
    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = home;
    }

    // Computed

    @JsonProperty("receiverID")
    public UUID getReceiverID() {
        return (receiver != null) ? receiver.getID() : null;
    }

    @JsonProperty("homeID")
    public Long getHomeID() {
        return (home != null) ? home.getID() : -1;
    }

    @JsonProperty("homeName")
    public String getHomeName() {
        return (home != null) ? home.getName() : "";
    }

    public boolean equalsWithoutID(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof HomeInvitationModel)) {
            return false;
        } else {
            HomeInvitationModel object = (HomeInvitationModel) obj;

            return (this.getIssuerID().equals(object.getIssuerID())
                    && this.getReceiver().equals(object.getReceiver())
                    && this.getHome().equals(object.getHome())
            );
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (equalsWithoutID(obj)) {
            HomeInvitationModel object = (HomeInvitationModel) obj;
            return (this.getID().equals(object.getID()));
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, issuerID, receiver, home);
    }
}

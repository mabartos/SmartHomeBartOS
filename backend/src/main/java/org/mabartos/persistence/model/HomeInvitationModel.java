package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.interfaces.Identifiable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@NamedQueries({
        @NamedQuery(name = "getHomesInvitations", query = "select inv from HomeInvitationModel inv where inv.home.id=:homeID"),
        @NamedQuery(name = "getUsersInvitations", query = "select inv from HomeInvitationModel inv where inv.receiver.id=:userID")
})
public class HomeInvitationModel extends PanacheEntityBase implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "INVITATION_ID")
    Long id;

    @Column
    private UUID issuerID;

    @Column(nullable = false)
    @ManyToOne
    private UserModel receiver;

    @Column(nullable = false)
    @ManyToOne
    private HomeModel home;

    public HomeInvitationModel() {
    }

    public HomeInvitationModel(UserModel receiver, HomeModel home) {
        this.receiver = receiver;
        this.home = home;
    }

    @Override
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

    public UUID getReceiverID() {
        return (receiver != null) ? receiver.getID() : null;
    }

    public Long getHomeID() {
        return (home != null) ? home.getID() : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof HomeInvitationModel)) {
            return false;
        } else {
            HomeInvitationModel object = (HomeInvitationModel) obj;

            return (this.getID().equals(object.getID())
                    && this.getIssuerID().equals(object.getIssuerID())
                    && this.getReceiver().equals(object.getReceiver())
                    && this.getHome().equals(object.getHome())
            );
        }
    }

    public int hashCode() {
        return Objects.hash(id, issuerID, receiver, home);
    }
}

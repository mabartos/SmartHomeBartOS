package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.interfaces.HasChildren;

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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
@Cacheable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "email"})
public class UserModel extends PanacheEntityBase implements HasChildren<HomeModel> {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    Long id;

    @Column(unique = true, nullable = false)
    private Long idExternalUser;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = HomeModel.class)
    @JoinTable(name = "USERS_HOMES",
            joinColumns = {
                    @JoinColumn(referencedColumnName = "USER_ID")},
            inverseJoinColumns = {
                    @JoinColumn(referencedColumnName = "HOME_ID")}
    )
    private Set<HomeModel> homesSet = new HashSet<>();

    public UserModel() {
    }

    public UserModel(String username) {
        this.username = username;
    }

    public UserModel(Long id, String username) {
        this(username);
        this.idExternalUser = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Long getID() {
        return idExternalUser;
    }

    @Override
    public void setID(Long id) {
        this.idExternalUser = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /* HOMES */
    @Override
    @JsonIgnore
    public Set<HomeModel> getChildren() {
        return homesSet;
    }

    @Override
    public boolean addChild(HomeModel child) {
        return homesSet.add(child);
    }

    @Override
    public boolean removeChild(HomeModel child) {
        return homesSet.remove(child);
    }

    @Override
    public boolean removeChildByID(Long id) {
        return homesSet.removeIf(home -> home.getID().equals(id));
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
        return Objects.hash(id, username, email);
    }
}

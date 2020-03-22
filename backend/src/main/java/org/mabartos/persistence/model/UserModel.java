package org.mabartos.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.general.UserRole;
import org.mabartos.interfaces.HasChildren;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
@JsonPropertyOrder({"id","name","email"})
public class UserModel extends PanacheEntityBase implements HasChildren<HomeModel> {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column(nullable = false)
    @Enumerated
    private UserRole userRole = UserRole.USER;

    @Column(unique = true)
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

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserModel(String username, String password, String email, String firstname, String lastname) {
        this(username, password, email);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
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

    /* COMPUTED */
    @JsonProperty("homesCount")
    public Integer getHomesCount() {
        if (homesSet != null) {
            return homesSet.size();
        }
        return 0;
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
                    && object.getFirstname().equals(this.getFirstname())
                    && object.getLastname().equals(this.getLastname())
                    && object.getEmail().equals(this.getEmail())
                    && object.getUserRole().equals(this.getUserRole())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstname, lastname, email, userRole);
    }
}

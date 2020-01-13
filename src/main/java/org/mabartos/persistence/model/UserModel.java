package org.mabartos.persistence.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.mabartos.general.UserRole;
import org.mabartos.utils.Identifiable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "Users")
@Cacheable
public class UserModel extends PanacheEntity implements Serializable, Identifiable {

    @Column(unique = true, nullable = false)
    public String username;

    @Column(nullable = false)
    public String password;

    @Column
    public String firstname;

    @Column
    public String lastname;

    @Column(nullable = false)
    @Enumerated
    public UserRole userRole = UserRole.USER;

    @Column(unique = true)
    @Email
    public String email;

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
}

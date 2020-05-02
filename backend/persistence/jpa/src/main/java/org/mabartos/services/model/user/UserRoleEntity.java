package org.mabartos.services.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.services.model.home.HomeEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "UserRoles")
@Cacheable
@NamedQueries({
        @NamedQuery(name = "deleteAllRolesFromHome", query = "delete from UserRoleEntity where home.id=:homeID"),
        @NamedQuery(name = "getAllUserRoleByUUID", query = "select roles from UserRoleEntity roles where roles.user.uuid=:userID")
})
public class UserRoleEntity extends PanacheEntityBase implements UserRoleModel {

    @Id
    @GeneratedValue
    @Column(name = "USER_ROLE_ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
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
    @JsonProperty("userID")
    public UUID getUserID() {
        return (user != null) ? user.getID() : null;
    }

    @JsonProperty("homeID")
    public Long getHomeID() {
        return (home != null) ? home.getID() : -1;
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

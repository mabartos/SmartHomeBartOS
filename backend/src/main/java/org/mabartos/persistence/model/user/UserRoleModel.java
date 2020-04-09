package org.mabartos.persistence.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mabartos.general.UserRole;
import org.mabartos.interfaces.Identifiable;
import org.mabartos.persistence.model.home.HomeModel;

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
        @NamedQuery(name = "deleteAllRolesFromHome", query = "delete from UserRoleModel where home.id=:homeID")
})
public class UserRoleModel extends PanacheEntityBase implements Identifiable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "USER_ROLE_ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "HOME_ID")
    private HomeModel home;

    @Enumerated
    @Column(nullable = false)
    private UserRole role = UserRole.HOME_MEMBER;

    public UserRoleModel() {
    }

    public UserRoleModel(UserModel user, HomeModel home, UserRole role) {
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
    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @JsonIgnore
    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        this.home = home;
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
        } else if (!(obj instanceof UserRoleModel)) {
            return false;
        } else {
            UserRoleModel model = (UserRoleModel) obj;

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

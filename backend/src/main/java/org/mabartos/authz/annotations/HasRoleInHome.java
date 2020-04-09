package org.mabartos.authz.annotations;

import org.mabartos.general.UserRole;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface HasRoleInHome {
    UserRole minRole() default UserRole.HOME_CHILD;

    UserRole[] roles() default {};

    boolean orIsOwner() default false;
}

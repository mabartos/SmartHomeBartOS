package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.UserModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserModel> {
}

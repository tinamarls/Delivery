package repository;

import models.User;

import java.util.Optional;

public interface UserRepository {

    Long saveUser(User user);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);
}

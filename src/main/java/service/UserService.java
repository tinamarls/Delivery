package service;

import models.User;

import java.util.Optional;

public interface UserService {

//    User signUp(String login, String password, String role);

    Optional<User> findUser(String email, String password);

    boolean isUserWithEmail(String email);

}

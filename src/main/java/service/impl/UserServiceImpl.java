package service.impl;

import models.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import security.PasswordSecurity;
import service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public Optional<User> findUser(String email, String password) {
        String hashPassword = PasswordSecurity.hashPassword(password);

        return userRepository.findUserByEmailAndPassword(email, hashPassword);
    }

    @Override
    public boolean isUserWithEmail(String email) {

        return userRepository.findUserByEmail(email).isPresent();

    }

}

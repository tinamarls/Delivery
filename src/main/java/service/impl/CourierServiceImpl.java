package service.impl;

import models.Courier;
import models.User;
import repository.CourierRepository;
import repository.UserRepository;
import repository.impl.CourierRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import security.PasswordSecurity;
import service.CourierService;

import java.util.List;
import java.util.Optional;

public class CourierServiceImpl implements CourierService {

    UserRepository userRepository = new UserRepositoryImpl();
    CourierRepository courierRepository = new CourierRepositoryImpl();

    @Override
    public void signUpNewCourier(String email, String password, String name, String phoneNumber) {

        String hashPassword = PasswordSecurity.hashPassword(password);

        User user = User.builder()
                .loginOfUSer(email)
                .password(hashPassword)
                .role("courier")
                .build();

        Long idOfUser = userRepository.saveUser(user);

        Courier courier = Courier.builder()
                .nameOfCourier(name)
                .numberOfPhone(phoneNumber)
                .userID(idOfUser)
                .build();

        courierRepository.saveCourierByIDUser(courier);
    }

    @Override
    public Optional<Courier> findByUserId(Long id) {
        return courierRepository.findByUserId(id);
    }

    @Override
    public void changeStatus(Courier courier, String status) {
        if(!courier.getStatus().equals(status)){
            courierRepository.changeStatus(courier, status);
        }
    }

    @Override
    public List<Courier> findAllCouriersByStatus(String status) {
        return courierRepository.findByStatus(status);
    }
}

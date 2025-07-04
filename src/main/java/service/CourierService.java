package service;

import models.Courier;

import java.util.List;
import java.util.Optional;

public interface CourierService {

    void signUpNewCourier(String email, String password, String name, String phoneNumber);

    Optional<Courier> findByUserId(Long id);

    void changeStatus(Courier courier, String status);

    List<Courier> findAllCouriersByStatus(String status);

}

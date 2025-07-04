package repository;

import models.Courier;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    void saveCourierByIDUser(Courier courier);

    Optional<Courier> findByUserId(Long id);

    void changeStatus(Courier courier, String status);

    List<Courier> findByStatus(String status);

}

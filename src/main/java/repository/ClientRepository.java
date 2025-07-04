package repository;

import models.Client;

import java.util.Optional;

public interface ClientRepository {

    void saveClientByIDUser(Client client);

    Optional<Client> findByIdUser(Long id);

    void update(Client client);

    Optional<Client> findById(Long id);

    void updateBonuses(Long id, int bonuses);

}

package service;

import models.Client;
import models.User;

import java.util.Optional;

public interface ClientService {

    User signUpNewClient(String email, String password);

    Optional<Client> findByIdUser(User user);

    void updateClient(Client client, String param, String value);

    Optional<Client> findClientById(Long id);

    void updateCountOfBonuses(Client client, int bonuses);
}

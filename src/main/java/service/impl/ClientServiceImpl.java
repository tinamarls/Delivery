package service.impl;

import models.Client;
import models.User;
import repository.ClientRepository;
import repository.UserRepository;
import repository.impl.ClientRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import security.PasswordSecurity;
import service.ClientService;

import java.sql.Date;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    UserRepository userRepository = new UserRepositoryImpl();
    ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public User signUpNewClient(String email, String password) {

        String hashPassword = PasswordSecurity.hashPassword(password);

        User user = User.builder()
                .loginOfUSer(email)
                .password(hashPassword)
                .role("client")
                .build();


        Long idForClient = userRepository.saveUser(user);

        Client client = Client.builder()
                .userID(idForClient)
                .build();

        clientRepository.saveClientByIDUser(client);

        return user;

    }

    @Override
    public Optional<Client> findByIdUser(User user) {

        Long idForSearch = user.getId();

        return clientRepository.findByIdUser(idForSearch);
    }

    @Override
    public void updateClient(Client client, String param, String value) {

        if(param.equals("phone")){
            client.setNumberOfPhone(value);
        } else if (param.equals("name")){
            client.setNameOfClient(value);
        } else {
            client.setDateOfBirth(Date.valueOf(value));
        }

        clientRepository.update(client);

    }

    @Override
    public Optional<Client> findClientById(Long id) {
        return (clientRepository.findById(id));
    }

    @Override
    public void updateCountOfBonuses(Client client, int bonuses) {
        clientRepository.updateBonuses(client.getId(), bonuses);
    }
}

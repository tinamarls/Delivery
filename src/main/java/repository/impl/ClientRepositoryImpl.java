package repository.impl;

import db.PostgresConnectionProvider;
import models.Client;
import repository.ClientRepository;

import java.sql.*;
import java.util.Optional;
import java.util.function.Function;

public class ClientRepositoryImpl implements ClientRepository {

    //language=SQL
    private static final String SQL_ADD_USER = "insert into client(user_id) values (?)";

    //language=SQL
    private static final String SQL_FIND_BY_ID_USER = "select * from client where user_id = ?;";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from client where id = ?;";

    //language=SQL
    private static final String SQL_UPDATE = "update client set name_of_client = ?, " +
            "phone_number = ?, date_of_birth = ? where id = ?";

    //language=SQL
    private static final String SQL_UPDATE_BONUSES = "update client set " +
            "bonuses = ? where id = ?;";

    private static final Function<ResultSet, Client> clientMapper = row -> {

        try {
            Long id = row.getLong("id");
            String name = row.getString("name_of_client");
            String phone_number = row.getString("phone_number");
            Date date = row.getDate("date_of_birth");
            Long user_id = row.getLong("user_id");
            Integer bonuses = row.getInt("bonuses");

            return new Client(id, name, phone_number, date, user_id, bonuses);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };

    @Override
    public void saveClientByIDUser(Client client) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, client.getUserID());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save new client");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                client.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Client> findByIdUser(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_USER)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    return Optional.ofNullable(clientMapper.apply(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Client client) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)){

            statement.setString(1, client.getNameOfClient());
            statement.setString(2, client.getNumberOfPhone());
            statement.setDate(3, client.getDateOfBirth());
            statement.setLong(4, client.getId());

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Can't update client");
            }

        } catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    return Optional.ofNullable(clientMapper.apply(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateBonuses(Long id, int bonuses) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BONUSES)){

            statement.setInt(1, bonuses);
            statement.setLong(2, id);

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Can't update bonuses client");
            }

        } catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }
}

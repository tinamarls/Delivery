package repository.impl;

import db.PostgresConnectionProvider;
import models.Courier;
import repository.CourierRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CourierRepositoryImpl implements CourierRepository {

    //language=SQL
    private static final String SQL_ADD_COURIER = "insert into courier(name_of_courier, phone_number, user_id) values (?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_BY_ID_USER = "select * from courier where user_id = ?;";

    //language=SQL
    private static final String SQL_UPDATE_STATUS = "update courier set status = ? where id = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_STATUS = "select * from courier where status = ?";


    private static final Function<ResultSet, Courier> courierMapper = row -> {

        try {
            Long id = row.getLong("id");
            String nameOfCourier = row.getString("name_of_courier");
            String numberOfPhone = row.getString("phone_number");
            Long userID = row.getLong("user_id");
            String status = row.getString("status");

            return new Courier(id, nameOfCourier, numberOfPhone, userID, status);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };

    @Override
    public void saveCourierByIDUser(Courier courier) {
        try(PreparedStatement statement = PostgresConnectionProvider.getConnection().prepareStatement(SQL_ADD_COURIER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, courier.getNameOfCourier());
            statement.setString(2, courier.getNumberOfPhone());
            statement.setLong(3, courier.getUserID());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save new courier");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                courier.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Courier> findByUserId(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_USER)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    return Optional.ofNullable(courierMapper.apply(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void changeStatus(Courier courier, String status) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)){

            statement.setString(1, status);
            statement.setLong(2, courier.getId());

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Can't update status courier");
            }

        } catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Courier> findByStatus(String status) {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Courier> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_STATUS);

            statement.setString(1, status);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(courierMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}

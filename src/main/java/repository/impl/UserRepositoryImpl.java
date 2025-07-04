package repository.impl;

import db.PostgresConnectionProvider;
import models.User;
import repository.UserRepository;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    //language=SQL
    private static final String SQL_ADD_USER = "insert into users(login_of_user, password_of_user, role_of_user) " +
            "values (?,?,?);";

    //language=SQL
    private static final String SQL_FIND_USER_BY_EMAIL_AND_PAS = "select * from users where login_of_user = ? and password_of_user = ?;";

    //language=SQL
    private static final String SQL_FIND_USER_BY_EMAIL = "select * from users where login_of_user = ?;";


    @Override
    public Long saveUser(User user) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getLoginOfUSer());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save lesson");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
                return generatedKeys.getLong("id");
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {

        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL_AND_PAS)) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            boolean hasOne = resultSet.next();


            if(hasOne){
                User user =  User.builder()
                        .id(resultSet.getLong("id"))
                        .loginOfUSer(resultSet.getString("login_of_user"))
                        .password(resultSet.getString("password_of_user"))
                        .role(resultSet.getString("role_of_user"))
                        .build();
                return Optional.ofNullable(user);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            boolean hasOne = resultSet.next();

            if(hasOne){
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .loginOfUSer(resultSet.getString("login_of_user"))
                        .password(resultSet.getString("password_of_user"))
                        .role(resultSet.getString("role_of_user"))
                        .build();
                return Optional.ofNullable(user);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }
}

package repository.impl;

import db.PostgresConnectionProvider;
import models.Order;
import models.ProductsOrder;
import repository.OrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class OrderRepositoryImpl implements OrderRepository {

    //language=SQL
    private static final String SQL_ADD_ORDER = "insert into ordering(name_of_client, number, full_price, status, address, comment_for_order, client_id)" +
            " values (?,?,?,?,?,?,?)";

    //language=SQL
    private static final String SQL_ADD_ALL_PRODUCTS_IN_ORDER = "insert into product_in_ordering(product_id, count_of_product, ordering_id) " +
            "values (?, ?, ?)";

    //language=SQL
    private static final String SQL_SELECT_FOR_CLIENT = "select * from ordering where client_id = ? and status = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL_ORDERS_BY_STATUS = "select * from ordering where status = ?";

    //language=SQL
    private static final String SQL_SELECT_FOR_COURIER = "select * from ordering where courier_id = ? and status = 'принят'";

    //language=SQL
    private static final String SQL_UPDATE_STATUS = "update ordering set status = ? where id = ?";

    //language=SQL
    private static final String SQL_UPDATE_COURIER = "update ordering set courier_id = ? where id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from ordering where id = ?;";

    private static final Function<ResultSet, Order> orderMapper = row -> {

        try {
            Long id = row.getLong("id");
            String name = row.getString("name_of_client");
            String phone = row.getString("number");
            Integer fullPrice = row.getInt("full_price");
            String status = row.getString("status");
            String address = row.getString("address");
            String comment = row.getString("comment_for_order");
            Long idClient = row.getLong("client_id");
            Long idCourier = row.getLong("courier_id");

            return new Order(id, name, phone, fullPrice, status, address, comment, idClient, idCourier);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };


    @Override
    public Long saveOrder(Order order) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, order.getNameOfClient());
            statement.setString(2, order.getPhoneNumber());
            statement.setInt(3, order.getFullPrice());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getAddress());
            statement.setString(6, order.getComment());
            statement.setLong(7, order.getIdClient());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save order");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong("id"));
                return generatedKeys.getLong("id");
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void saveAllProductsInOrder(ProductsOrder productsOrder) {
        try(PreparedStatement statement = PostgresConnectionProvider.getConnection()
                .prepareStatement(SQL_ADD_ALL_PRODUCTS_IN_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, productsOrder.getIdProduct());
            statement.setInt(2, productsOrder.getCount());
            statement.setLong(3, productsOrder.getIdOrder());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save order");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                productsOrder.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Order> selectAllOrderClientByStatus(Long id, String status) {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Order> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FOR_CLIENT);

            statement.setLong(1, id);
            statement.setString(2, status);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(orderMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<Order> selectAllOrderCourier(Long id) {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Order> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FOR_COURIER);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(orderMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<Order> selectAllOrdersByStatus(String status) {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Order> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_STATUS);

            statement.setString(1, status);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(orderMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public void updateStatusOrder(Long id, String status) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)){

            statement.setString(1, status);
            statement.setLong(2, id);

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Can't update client");
            }

        } catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    return Optional.ofNullable(orderMapper.apply(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateCourierInOrder(Long idCourier, Long idOrder) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COURIER)){

            statement.setLong(1, idCourier);
            statement.setLong(2, idOrder);

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Can't update client");
            }

        } catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }

}

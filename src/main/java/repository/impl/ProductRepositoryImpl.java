package repository.impl;

import db.PostgresConnectionProvider;
import models.Product;
import repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProductRepositoryImpl implements ProductRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from product";

    //language=SQL
    private static final String SQL_ADD = "insert into product(name_of_product, price, category_of_product, description, photo) " +
            "values (?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_SELECT_BY_CATEGORY = "select * from product where category_of_product = ?;";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from product where id = ?;";

    //language=SQL
    private static final String SQL_DELETE = "delete from product_in_ordering where product_id = ?;" +
            "delete from product where id = ?";

    //language=SQL
    private static final String SQL_UPDATE_LESSON = "update product set name_of_product = ?," +
            "price = ?, category_of_product = ?, description = ?, photo = ? where id = ?";

    //language=SQL
    private static final String SQL_SELECT_FOR_ORDER =
            "select * from product p " +
                    "inner join product_in_ordering pio on p.id = pio.product_id " +
                    "where pio.ordering_id = ?";


    private static final Function<ResultSet, Product> rowsMapper = row -> {

        try {
            Long id = row.getLong("id");
            String name = row.getString("name_of_product");
            Integer price = row.getInt("price");
            String category = row.getString("category_of_product");
            String description = row.getString("description");
            String photo = row.getString("photo");

            return Product.builder().id(id).nameOfProduct(name).price(price)
                    .category(category).description(description).photoNameFile(photo).build();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };

    private static final Function<ResultSet, Product> productInOrderMapper = row -> {

        try {
            Long id = row.getLong("id");
            String name = row.getString("name_of_product");
            Integer price = row.getInt("price");
            String category = row.getString("category_of_product");
            String description = row.getString("description");
            Integer count = row.getInt("count_of_product");
            String photo = row.getString("photo");

            return new Product(id, name, price, category, description, count, photo);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };

    @Override
    public List<Product> getAllProducts() {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Product> list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while(resultSet.next()){
                list.add(rowsMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public void save(Product product) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getNameOfProduct());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getCategory());
            statement.setString(4, product.getDescription());
            statement.setString(5, product.getPhotoNameFile());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save lesson");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Product> getProductByCategory(String name) {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Product> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_CATEGORY);

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(rowsMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    return Optional.ofNullable(rowsMapper.apply(resultSet));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Product> findAllProductsInOrder(Long idOrder) {
        Connection connection = PostgresConnectionProvider.getConnection();

        List<Product> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FOR_ORDER);

            statement.setLong(1, idOrder);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(productInOrderMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public void delete(Long id) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE)){

            statement.setLong(1, id);
            statement.setLong(2, id);

            int affectedRows = statement.executeUpdate();

        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Product productOld, Product productNew) {
        try(Connection connection = PostgresConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_LESSON)){

            // в зависимости от того, какие данные ввел пользователь, смотрим,
            // какие именно значение нужно обновить, а какие оставить прежними
            if(productNew.getNameOfProduct().equals("")){
                statement.setString(1, productOld.getNameOfProduct());
            } else{
                statement.setString(1, productNew.getNameOfProduct());
            }

            if(productNew.getPrice() == -100){
                statement.setInt(2, productOld.getPrice());
            } else{
                statement.setInt(2, productNew.getPrice());
            }

            if(productNew.getCategory().equals("")){
                statement.setString(3, productOld.getCategory());
            } else{
                statement.setString(3, productNew.getCategory());
            }

            if(productNew.getDescription().equals("")){
                statement.setString(4, productOld.getDescription());
            } else{
                statement.setString(4, productNew.getDescription());
            }

            if(productNew.getPhotoNameFile().equals("")){
                statement.setString(5, productOld.getPhotoNameFile());
            } else{
                statement.setString(5, productNew.getPhotoNameFile());
            }


            statement.setLong(6, productOld.getId());

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Can't update product");
            }

        } catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }

}

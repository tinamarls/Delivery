package repository;

import models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAllProducts();

    void save(Product product);

    List<Product> getProductByCategory(String name);

    Optional<Product> findProductById(Long id);

    List<Product> findAllProductsInOrder(Long idOrder);

    void delete(Long id);

    void update(Product productOld, Product productNew);

}

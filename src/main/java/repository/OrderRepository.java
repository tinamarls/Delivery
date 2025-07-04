package repository;

import models.Order;
import models.ProductsOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Long saveOrder(Order order);

    void saveAllProductsInOrder(ProductsOrder productsOrder);

    List<Order> selectAllOrderClientByStatus(Long id, String status);

    List<Order> selectAllOrderCourier(Long id);

    List<Order> selectAllOrdersByStatus(String status);

    void updateStatusOrder(Long id, String status);

    Optional<Order> findOrderById(Long id);

    void updateCourierInOrder(Long idCourier, Long idOrder);
}

package service;

import jakarta.servlet.http.HttpServletRequest;
import models.Order;
import models.Product;

import java.util.List;
import java.util.Map;

public interface OrderService {

    boolean registrationOrder(HttpServletRequest req);

    Map<Order, List<Product>> findOrdersForClient(Long idClient, String status);

    Map<Order, List<Product>> findOrdersForCourier(Long idCourier);

    void finishOrder(Long id);

    Map<Order, List<Product>> findOrdersByStatus(String status);

    void updateCourierForOrder(Long idCourier, Long idOrder);
}

package service.impl;

import jakarta.servlet.http.HttpServletRequest;
import models.*;
import repository.ClientRepository;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.impl.ClientRepositoryImpl;
import repository.impl.OrderRepositoryImpl;
import repository.impl.ProductRepositoryImpl;
import service.OrderService;
import servlets.listeners.AttributeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository = new OrderRepositoryImpl();

    ProductRepository productRepository = new ProductRepositoryImpl();

    ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public boolean registrationOrder(HttpServletRequest req) {

        User user = (User) req.getSession().getAttribute("user");
        Long idUser = user.getId();

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String comment = req.getParameter("comment");

        if(name != null && phone != null && address != null){
            if(!name.isEmpty() && !phone.isEmpty() && !address.isEmpty()){
                if(clientRepository.findByIdUser(idUser).isPresent()){

                    Client client = clientRepository.findByIdUser(idUser).get();
                    Long idClient = client.getId();

                    Order order = Order.builder()
                            .nameOfClient(name)
                            .phoneNumber(phone)
                            .fullPrice((Integer) req.getSession().getAttribute("fullPrice"))
                            .status("принят")
                            .address(address)
                            .comment(comment)
                            .idClient(idClient)
                            .build();


                    Long idOrder = orderRepository.saveOrder(order);

                    Map<Product, Integer> allProducts = AttributeListener.getBag();

                    for (Map.Entry<Product, Integer> position : allProducts.entrySet()) {

                        ProductsOrder productsOrder = ProductsOrder.builder()
                                .idProduct(position.getKey().getId())
                                .count(position.getValue())
                                .idOrder(idOrder)
                                .build();

                        orderRepository.saveAllProductsInOrder(productsOrder);

                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Map<Order, List<Product>> findOrdersForClient(Long idClient, String status) {

        Map<Order, List<Product>> fullOrders = new HashMap<>();

        List<Order> orders = orderRepository.selectAllOrderClientByStatus(idClient, status);

        for (Order order : orders){
            List<Product> productsInOrder = productRepository.findAllProductsInOrder(order.getId());

            fullOrders.put(order, productsInOrder);
        }

        return fullOrders;
    }

    @Override
    public Map<Order, List<Product>> findOrdersForCourier(Long idCourier) {

        Map<Order, List<Product>> fullOrders = new HashMap<>();

        List<Order> orders = orderRepository.selectAllOrderCourier(idCourier);

        for (Order order : orders){
            List<Product> productsInOrder = productRepository.findAllProductsInOrder(order.getId());

            fullOrders.put(order, productsInOrder);
        }

        return fullOrders;

    }

    @Override
    public void finishOrder(Long id) {

        // по заказу находим клиента, которому будет добавлять бонусы

        if(orderRepository.findOrderById(id).isPresent()){
            Order order = orderRepository.findOrderById(id).get();
            if(clientRepository.findById(order.getIdClient()).isPresent()){

                Client client = clientRepository.findById(order.getIdClient()).get();

                int countBonuses = (int) (order.getFullPrice() * 0.15);

                clientRepository.updateBonuses(client.getId(), countBonuses);

                orderRepository.updateStatusOrder(id, "выполнен");
            }
        }
    }

    @Override
    public Map<Order, List<Product>> findOrdersByStatus(String status) {
        Map<Order, List<Product>> fullOrders = new HashMap<>();

        List<Order> orders = orderRepository.selectAllOrdersByStatus(status);

        for (Order order : orders){
            List<Product> productsInOrder = productRepository.findAllProductsInOrder(order.getId());

            fullOrders.put(order, productsInOrder);
        }

        return fullOrders;
    }

    @Override
    public void updateCourierForOrder(Long idCourier, Long idOrder) {
        orderRepository.updateCourierInOrder(idCourier, idOrder);
    }

}

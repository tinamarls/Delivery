package servlets.ordering;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Client;
import models.Product;
import service.ClientService;
import service.OrderService;
import servlets.listeners.AttributeListener;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    OrderService orderService;
    ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        clientService = (ClientService) getServletContext().getAttribute("clientService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Map<Product, Integer> allProduct = AttributeListener.getBag();

        int price = (int) session.getAttribute("fullPrice");

        // списание бонусов
        if(req.getParameter("useBonuses") != null){

            Long idOfClient = Long.valueOf(req.getParameter("useBonuses"));

            if(clientService.findClientById(idOfClient).isPresent()){

                Client client = clientService.findClientById(idOfClient).get();

                int bonuses = client.getBonuses();
                int balance;
                int newPrice;

                if(bonuses > price){
                    balance = bonuses - price;
                    newPrice = 0;
                } else {
                    balance = 0;
                    newPrice = price - bonuses;
                }

                session.setAttribute("fullPrice", newPrice);

                clientService.updateCountOfBonuses(client, balance);
            }

        }

        getServletContext().getRequestDispatcher("/WEB-INF/orderPages/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(orderService.registrationOrder(req)){
            session.removeAttribute("allProducts");
            session.removeAttribute("messageForOrder");
            resp.sendRedirect("/account");
        } else{
            session.setAttribute("messageForOrder", "Чтобы оформить заказ необходимо указать имя, телефон и адрес");
            resp.sendRedirect("/order");
        }

        // после выполнения заказа, корзина опустошается

    }
}

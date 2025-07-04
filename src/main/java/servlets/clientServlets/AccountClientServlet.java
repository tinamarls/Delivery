package servlets.clientServlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Client;
import models.Order;
import models.Product;
import models.User;
import service.AuthUserService;
import service.ClientService;
import service.OrderService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/account")
public class AccountClientServlet extends HttpServlet {

    ClientService clientService;
    OrderService orderService;
    AuthUserService authUserService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        clientService = (ClientService) getServletContext().getAttribute("clientService");
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        authUserService = (AuthUserService) getServletContext().getAttribute("authUserService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        // для отображения данных в личном кабинете пользователя
        if(clientService.findByIdUser(user).isPresent()){
            Client client = clientService.findByIdUser(user).get();
            req.setAttribute("client", client);

            // история заказов пользователя
            Map<Order, List<Product>> ordersDone = orderService.findOrdersForClient(client.getId(), "выполнен");
            req.setAttribute("doneOrders", ordersDone);

            // заказы, которые сейчас готовятся
            Map<Order, List<Product>> ordersPreparing = orderService.findOrdersForClient(client.getId(), "принят");
            req.setAttribute("preparingOrders", ordersPreparing);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/clientPages/clientAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if(clientService.findByIdUser(user).isPresent()){
            Client client = clientService.findByIdUser(user).get();

            // заполнение данных в аккаунте пользователя
            if(req.getParameter("name") != null && !req.getParameter("name").isEmpty()){
                clientService.updateClient(client, "name", req.getParameter("name"));
            }

            if(req.getParameter("phone") != null && !req.getParameter("phone").isEmpty()){
                clientService.updateClient(client, "phone", req.getParameter("phone"));
            }

            if(req.getParameter("date") != null && !req.getParameter("date").isEmpty()){
                clientService.updateClient(client, "date", req.getParameter("date"));
            }

        }

        resp.sendRedirect("/account");

    }
}
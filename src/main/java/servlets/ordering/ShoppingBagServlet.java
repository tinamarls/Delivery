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
import models.User;
import service.AuthUserService;
import service.ClientService;
import servlets.listeners.AttributeListener;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/bag")
public class ShoppingBagServlet extends HttpServlet {

    ClientService clientService;
    AuthUserService authUserService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        clientService = (ClientService) getServletContext().getAttribute("clientService");
        authUserService = (AuthUserService) getServletContext().getAttribute("authUserService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Map<Product, Integer> allProductsInOrder = AttributeListener.getBag();

        session.setAttribute("allProducts", allProductsInOrder);

        int fullPrice = 0;

        for (Map.Entry<Product, Integer> set :
                allProductsInOrder.entrySet()) {

            fullPrice += set.getValue() * set.getKey().getPrice();
        }

        req.getSession().setAttribute("fullPrice", fullPrice);

        if(authUserService.isAuthorization(req, resp)){
            User user = authUserService.getUser(req, resp);
            if(clientService.findByIdUser(user).isPresent()){
                Client client = clientService.findByIdUser(user).get();
                req.setAttribute("client", client);
            }
        }

        req.getRequestDispatcher("/WEB-INF/orderPages/bag.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(req.getParameter("plus") != null){
            session.setAttribute("idForProduct", req.getParameter("plus"));
        }

        if(req.getParameter("minus") != null){
            session.setAttribute("idForMinus", req.getParameter("minus"));
        }

        if(req.getParameter("delete") != null){
            session.setAttribute("deleteSomeProduct", req.getParameter("delete"));
        }

        resp.sendRedirect("/bag");
    }
}

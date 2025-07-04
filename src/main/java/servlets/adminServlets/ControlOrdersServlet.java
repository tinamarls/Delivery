package servlets.adminServlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Courier;
import models.Order;
import models.Product;
import service.CourierService;
import service.OrderService;
import service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/admin")
public class ControlOrdersServlet extends HttpServlet {

    OrderService orderService;
    CourierService courierService;
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        courierService = (CourierService) getServletContext().getAttribute("courierService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<Order, List<Product>> allOrders = orderService.findOrdersByStatus("принят");
        req.setAttribute("orders", allOrders);

        List<Courier> couriers = courierService.findAllCouriersByStatus("работает");
        req.setAttribute("couriers", couriers);

        getServletContext().getRequestDispatcher("/WEB-INF/adminPages/controlOrders.jsp").forward(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("idCourier") != null && req.getParameter("idOrder") != null){
            Long idCourier = Long.valueOf(req.getParameter("idCourier"));
            Long idOrder = Long.valueOf(req.getParameter("idOrder"));
            orderService.updateCourierForOrder(idCourier,idOrder);
        }

        // добавление нового курьера
        String email = req.getParameter("emailForRegCourier");
        String password = req.getParameter("passwordForRegCourier");
        String name = req.getParameter("nameOfCourier");
        String phoneNumber = req.getParameter("phoneNumber");


        if (email != null && password != null && name != null && phoneNumber != null){
            if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !phoneNumber.isEmpty()){
                if(!userService.isUserWithEmail(email)){
                    courierService.signUpNewCourier(email, password, name, phoneNumber);
                }
            }


        }

        resp.sendRedirect("/admin");
    }
}

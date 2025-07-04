package servlets.courierServlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Courier;
import models.Order;
import models.Product;
import models.User;
import service.AuthUserService;
import service.CourierService;
import service.OrderService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/courier")
public class EmployeeServlet extends HttpServlet {

    AuthUserService authUserService;
    CourierService courierService;
    OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authUserService = (AuthUserService) getServletContext().getAttribute("authUserService");
        courierService = (CourierService) getServletContext().getAttribute("courierService");
        orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = authUserService.getUser(req, resp);
        if(courierService.findByUserId(user.getId()).isPresent()){
            Courier courier = courierService.findByUserId(user.getId()).get();

            if(courier.getStatus().equals("отдыхает")){
                req.setAttribute("status", "отдыхает");
            } else{
                req.setAttribute("status", "работает");
            }

            Map<Order, List<Product>> orders = orderService.findOrdersForCourier(courier.getId());
            req.setAttribute("orders", orders);
        } else{
            req.setAttribute("message", "Не найдено такого курьера");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/courierPages/work.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = authUserService.getUser(req, resp);
        if(courierService.findByUserId(user.getId()).isPresent()){
            Courier courier = courierService.findByUserId(user.getId()).get();

            if(req.getParameter("work") != null){
                if(req.getParameter("work").equals("work")){
                    courierService.changeStatus(courier, "работает");
                    req.setAttribute("status", "работает");
                }

                if(req.getParameter("work").equals("relax")){
                    Map<Order, List<Product>> orders = orderService.findOrdersForCourier(courier.getId());
                    if(orders.size() == 0){
                        courierService.changeStatus(courier, "отдыхает");
                        req.setAttribute("status", "отдыхает");
                        req.getSession().setAttribute("message", "");
                    } else{
                        req.getSession().setAttribute("message", "Выполните все заказы, чтобы завершить смену");
                    }
                }
            }

            if(req.getParameter("finish") != null){
                Long idOrder = Long.valueOf(req.getParameter("finish"));
                orderService.finishOrder(idOrder);
            }
        } else{
            req.setAttribute("message", "Не найдено такого курьера");
        }

        resp.sendRedirect("/courier");
    }
}

package servlets.adminServlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;
import repository.ProductRepository;
import service.CourierService;
import service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/changes")
public class ChangesMenuServlet extends HttpServlet {

    CourierService courierService;
    UserService userService;
    ProductRepository productRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        courierService = (CourierService) getServletContext().getAttribute("courierService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Product> allProducts = productRepository.getAllProducts();
        req.setAttribute("products", allProducts);

        getServletContext().getRequestDispatcher("/WEB-INF/adminPages/changesMenu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getParameter("idDelete") != null){
            productRepository.delete(Long.valueOf(req.getParameter("idDelete")));
        }

        resp.sendRedirect("/admin/changes");

    }
}


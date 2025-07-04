package servlets.general;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.ProductRepository;
import service.AuthUserService;

import java.io.IOException;



@WebServlet(urlPatterns = "/")
public class MainServlet extends HttpServlet {

    ProductRepository productRepository;
    AuthUserService authUserService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authUserService = (AuthUserService) getServletContext().getAttribute("authUserService");
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("pizza", productRepository.getProductByCategory("Пицца"));
        req.setAttribute("snacks", productRepository.getProductByCategory("Закуски"));
        req.setAttribute("drinks", productRepository.getProductByCategory("Напитки"));
        req.setAttribute("deserts", productRepository.getProductByCategory("Десерты"));
        req.setAttribute("products", productRepository.getAllProducts());

        req.getRequestDispatcher("/WEB-INF/commonPages/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(req.getParameter("idForProduct") != null){
            session.setAttribute("idForProduct", req.getParameter("idForProduct"));
        }

        if(req.getParameter("exit") != null){
            authUserService.exit(req, resp);
        }

        resp.sendRedirect("/");
    }
}

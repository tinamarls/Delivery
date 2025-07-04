package servlets.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import repository.impl.ProductRepositoryImpl;
import service.impl.*;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("userService", new UserServiceImpl());
        sce.getServletContext().setAttribute("authUserService", new AuthUserServiceImpl());
        sce.getServletContext().setAttribute("clientService", new ClientServiceImpl());
        sce.getServletContext().setAttribute("courierService", new CourierServiceImpl());
        sce.getServletContext().setAttribute("productRepository", new ProductRepositoryImpl());
        sce.getServletContext().setAttribute("orderService", new OrderServiceImpl());
        sce.getServletContext().setAttribute("productService", new ProductServiceImpl());
        sce.getServletContext().setAttribute("fileService", new FileServiceImpl());

        sce.getServletContext().setAttribute("storagePath", "C:\\tomcat10\\img");
    }
}

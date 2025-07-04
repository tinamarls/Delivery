package servlets.securityServlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.AuthUserService;
import service.UserService;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    UserService userService;
    AuthUserService authUserService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        userService = (UserService) getServletContext().getAttribute("userService");
        authUserService = (AuthUserService) getServletContext().getAttribute("authUserService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/securityPages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("loginForAuth");
        String password = req.getParameter("passwordForAuth");

        if(email != null && password != null){
            if(!email.isEmpty() && !password.isEmpty()){
                if(userService.findUser(email, password).isPresent()){

                    User user = userService.findUser(email, password).get();

                    authUserService.authenticate(user, req, resp);

                    if(authUserService.getRole(req, resp).equals("client")){
                        resp.sendRedirect("/");
                    } else if (authUserService.getRole(req, resp).equals("admin")) {
                        resp.sendRedirect("/admin");
                    } else if (authUserService.getRole(req, resp).equals("courier")) {
                        resp.sendRedirect("/courier");
                    }

                } else{

                    if(userService.isUserWithEmail(email)){

                        req.getSession().setAttribute("messageForEntry", "Неправильный пароль, попробуйте снова");
                        resp.sendRedirect("/login");

                    } else {
                        req.getSession().setAttribute("messageForEntry", "Пользователя с такой почтой нет, зарегистрируйтесь");
                        resp.sendRedirect("/registration");
                    }

                }
            }
        } else{
            req.getSession().setAttribute("messageForEntry", "Логин и пароль не могут быть пустыми");
            resp.sendRedirect("/login");
        }

    }
}


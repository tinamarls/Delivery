package servlets.securityServlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.AuthUserService;
import service.ClientService;
import service.UserService;

import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    UserService userService;
    AuthUserService authUserService;
    ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        userService = (UserService) getServletContext().getAttribute("userService");
        authUserService = (AuthUserService) getServletContext().getAttribute("authUserService");
        clientService = (ClientService) getServletContext().getAttribute("clientService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/securityPages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("loginForReg");
        String password = req.getParameter("passwordForReg");

        if(login != null && password != null){
            if(!login.isEmpty() && !password.isEmpty()){
                if(!userService.isUserWithEmail(login)){
                    User user = clientService.signUpNewClient(login, password);
                    authUserService.authenticate(user, req, resp);
                    resp.sendRedirect("/");
                } else{
                    req.getSession().setAttribute("messageForEntry", "Уже есть аккаунт с такой почтой, войдите");
                    resp.sendRedirect("/login");
                }
            }
        } else {
            req.getSession().setAttribute("messageForEntry", "Логин и пароль не могут быть пустыми");
            resp.sendRedirect("/registration");
        }

    }

}

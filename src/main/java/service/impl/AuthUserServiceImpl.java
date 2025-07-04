package service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.AuthUserService;

public class AuthUserServiceImpl implements AuthUserService {


    @Override
    public void authenticate(User user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("role", user.getRole());
    }

    @Override
    public boolean isAuthorization(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("user") != null;
    }

    @Override
    public User getUser(HttpServletRequest req, HttpServletResponse resp) {
        return (User) req.getSession().getAttribute("user");
    }

    @Override
    public String getRole(HttpServletRequest req, HttpServletResponse resp) {
        return (String) req.getSession().getAttribute("role");
    }

    @Override
    public void exit(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("user");
        req.getSession().removeAttribute("role");
    }
}

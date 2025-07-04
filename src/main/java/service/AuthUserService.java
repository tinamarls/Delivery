package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

public interface AuthUserService {

    void authenticate(User user, HttpServletRequest req, HttpServletResponse resp);

    boolean isAuthorization(HttpServletRequest req, HttpServletResponse resp);

    User getUser(HttpServletRequest req, HttpServletResponse resp);

    String getRole(HttpServletRequest req, HttpServletResponse resp);

    void exit(HttpServletRequest req, HttpServletResponse resp);

}

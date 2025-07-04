package servlets.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthUserService;
import service.impl.AuthUserServiceImpl;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    // просмотреть по поводу хранения сервисов и репозиториев

    private final AuthUserService authUserService = new AuthUserServiceImpl();

    private static final String[] pathsForAdmin = new String[]{"/admin", "/admin/changes", "/admin/changes/product", "/admin/changes/add"};
    private static final String[] pathsForClient = new String[]{"/account", "/order"};
    private static final String[] pathsForCourier = new String[]{"/courier"};

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        boolean needAuth = false;
        String needRole = null;

        // проверка какой именно сервлет рассматривается и какая роль ваще нужна
        for(String path: pathsForClient){
            if(path.equals(req.getRequestURI().substring(req.getContextPath().length()))){
                needAuth = true;
                needRole = "client";
                break;
            }
        }
        if(!needAuth){
            for(String path: pathsForAdmin){
                if(path.equals(req.getRequestURI().substring(req.getContextPath().length()))){
                    needAuth = true;
                    needRole = "admin";
                    break;
                }
            }
        }
        if(!needAuth){
            for(String path: pathsForCourier){
                if(path.equals(req.getRequestURI().substring(req.getContextPath().length()))){
                    needAuth = true;
                    needRole = "courier";
                    break;
                }
            }
        }

        if(!needAuth){
            chain.doFilter(req, resp);
        } else{
            if(authUserService.isAuthorization(req, resp) && needRole.equals(authUserService.getRole(req, resp))){
                chain.doFilter(req, resp);
            } else{
                req.getRequestDispatcher("/login").forward(req, resp);
            }
        }
    }
}

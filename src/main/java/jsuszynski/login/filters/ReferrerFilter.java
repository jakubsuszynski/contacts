package jsuszynski.login.filters;


import jsuszynski.login.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(
        filterName = "Referrer",
        urlPatterns = {"/*"})
public class ReferrerFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //if logged user tries to login or register - redirect him
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (req.getRequestURL().toString().contains("login") || req.getRequestURL().toString().contains("register")) {
            Optional<User> loggedUser = Optional.ofNullable((User)req.getSession().getAttribute("user"));
            if (loggedUser.isPresent()) {
                resp.sendRedirect("error.jsp");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

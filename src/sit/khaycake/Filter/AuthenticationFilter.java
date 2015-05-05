package sit.khaycake.Filter;

import sit.khaycake.model.Customer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pasut on 5/5/2558.
 */
public class AuthenticationFilter implements Filter {
    private FilterConfig config;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        Customer customer = (Customer)request.getSession().getAttribute("customer");
        if(customer!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            response.sendRedirect("/????/?traget="+request.getRequestURI());
        }

    }

    @Override
    public void destroy() {

    }
}

package sit.khaycake.Filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Pasuth on 22/4/2558.
 */
public class jsonFilter implements Filter {
    FilterConfig config;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);
        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");
    }

    @Override
    public void destroy() {


    }
}

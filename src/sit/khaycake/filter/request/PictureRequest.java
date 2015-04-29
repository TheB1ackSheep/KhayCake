package sit.khaycake.filter.request;

import sit.khaycake.filter.MyFilter;
import sit.khaycake.filter.RequestValidation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Normalizer;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public class PictureRequest extends MyFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestValidation req = new RequestValidation((HttpServletRequest)servletRequest);

        if(req.getMethod().equalsIgnoreCase("POST"))
            req.validateMultipart(attribute("pictures", "รูปภาพ"));
        filterChain.doFilter(servletRequest, servletResponse);
    }


        @Override
    public void destroy() {

    }
}

package sit.khaycake.Filter.request;

import sit.khaycake.Filter.MyFilter;
import sit.khaycake.Filter.RequestValidation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 29/4/2558.
 */
public class ProductRequest extends MyFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if(request.getMethod().equalsIgnoreCase("post")) {
            RequestValidation req = new RequestValidation(request,response);
            req.validate(attribute("name", "ชื่อเค้ก"), attribute("detail", "รายละเอียด"),
                    integerAttribute("unit_id", "หน่วยเค้ก"), integerAttribute("cat_id", "ชนิดเค้ก"),
                    floatAttribute("cost", "ราคาต้นทุน"), floatAttribute("price", "ราคาขาย"),
                    integerAttribute("sale_qty", "จำนวนขาย", false),
                    integerAttribute("sale_price", "ราคาขาย", false));


        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

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
            req.validate(attribute("NAME", "ชื่อเค้ก"), attribute("DETAIL", "รายละเอียด"),
                    integerAttribute("UNIT_ID", "หน่วยเค้ก"), integerAttribute("CAT_ID", "ชนิดเค้ก"),
                    floatAttribute("COST", "ราคาต้นทุน"), floatAttribute("PRICE", "ราคาขาย"),
                    integerAttribute("SALE_QTY", "จำนวนขาย", false),
                    integerAttribute("SALE_PRICE", "ราคาขาย", false));


        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

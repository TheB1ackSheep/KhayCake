package sit.khaycake.filter.request;

import sit.khaycake.filter.MyFilter;
import sit.khaycake.filter.RequestValidation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Falook Glico on 4/26/2015.
 */
@WebFilter(filterName = "ProductRequest", urlPatterns = "/product/*")
public class ProductRequest extends MyFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        RequestValidation request = new RequestValidation((HttpServletRequest)servletRequest);

        String[] urlFragment = request.getRequestURI().split("/");
        String method = null;
        if(urlFragment.length > 3)
            method = urlFragment[3];

        if(request.getMethod().equalsIgnoreCase("POST"))
            if(method == null)
                request.validate(attribute("name", "ชื่อเค้ก"),attribute("detail", "รายละเอียด"),integerAttribute("pic_id","รูปภาพ")
                        , integerAttribute("unit", "หน่วยนับ"), integerAttribute("category", "ชนิด"), floatAttribute("cost", "ราคาต้นทุน")
                        , floatAttribute("price", "ราคาขาย"), integerAttribute("qty_sale","จำนวนเค้กราคาพิเศษ",false) ,
                        floatAttribute("price_sale","จำนวนเค้กราคาพิเศษ",false));


        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}

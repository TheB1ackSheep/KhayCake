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
        String resource = null;
        if(request.getPathInfo() != null)
            resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);

        if(request.getMethod().equalsIgnoreCase("post") && (resource != null && !resource.equalsIgnoreCase("delete"))) {
            RequestValidation req = new RequestValidation(request,response);
            if(req.validate(attribute("name", "ชื่อเค้ก"), attribute("detail", "รายละเอียด"),
                    integerAttribute("unit_id", "หน่วยเค้ก"), integerAttribute("cat_id", "ชนิดเค้ก"),
                    integerAttribute("pic_id", "รูปภาพ"),
                    floatAttribute("cost", "ราคาต้นทุน"), floatAttribute("price", "ราคาขาย"),
                    integerAttribute("sale_qty", "จำนวนขาย", false),
                    integerAttribute("sale_price", "ราคาขาย", false))){
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

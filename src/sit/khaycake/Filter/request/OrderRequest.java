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
public class OrderRequest extends MyFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if(request.getMethod().equalsIgnoreCase("post")) {
            RequestValidation req = new RequestValidation(request,response);
            req.validate(attribute("ORDER_DATE", "วันที่สั่ง"),floatAttribute("TOTAL_QTY", "จำนวนทั้งหมด"), floatAttribute("TOTAL_PRICE", "จำเงินทั้งหมด"),
                    integerAttribute("ORST_ID", "สถานะสั่งซื้อ"), integerAttribute("SHME_ID", "รหัสประเภทการส่ง"),
                    integerAttribute("SHTR_ID", "SHTR_ID",false), integerAttribute("CUST_ID", "รหัสลูกค้า"));
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

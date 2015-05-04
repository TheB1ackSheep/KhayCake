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
public class PaymentRequest extends MyFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if(request.getMethod().equalsIgnoreCase("post")) {
            RequestValidation req = new RequestValidation(request,response);
            req.validate(attribute("DATE_TIME", "วันเวลา"), integerAttribute("ORDER_ID", "รหัสคำสั่งซื้อ"), floatAttribute("AMOUNT", "จำนวนทั้งหมด"),
                    integerAttribute("BAAC_ID", "รหัสบัญชี"), integerAttribute("PAST_ID", "รหัสการชำระเงิน"));


        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

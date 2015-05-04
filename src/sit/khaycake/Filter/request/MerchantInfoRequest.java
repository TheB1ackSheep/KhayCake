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
public class MerchantInfoRequest extends MyFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if(request.getMethod().equalsIgnoreCase("post")) {
            RequestValidation req = new RequestValidation(request,response);
            req.validate(integerAttribute("ADDR_ID", "รหัสที่อยู่"), attribute("NAME","ชื่อผู้ขาย"),
                    attribute("PHONE","เบอร์โทรศัพท์"), attribute("FAX","เบอร์แฟกซ์"),
                    attribute("VAT_ID","รหัสประจำตัวผู้เสียภาษี"),floatAttribute("VAT_VALUE","มูลค่าภาษี"));
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

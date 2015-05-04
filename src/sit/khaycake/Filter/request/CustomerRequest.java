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
public class CustomerRequest extends MyFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if(request.getMethod().equalsIgnoreCase("post")) {
            RequestValidation req = new RequestValidation(request,response);
            req.validate(attribute("FNAME", "ชื่อ"),attribute("LNAME", "นามสกุล"),attribute("SEX", "เพศ"),attribute("DOB", "วัน/เดือน/ปีเกิด"),
                    attribute("PHONE", "เบอร์โทรศัพท์"), attribute("EMAIL", "อีเมลล์"), attribute("VAT_ID", "รหัสประจำตัวผู้เสียภาษี")
                    , integerAttribute("CAT_PARENT_ID", "ประเภทเค้ก"), attribute("PWD", "รหัสผ่าน"));
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}


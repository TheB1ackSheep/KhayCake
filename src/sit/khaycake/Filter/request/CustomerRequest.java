package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/5/2015.
 */
public class CustomerRequest  extends RequestValidation {

    public CustomerRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(emailAttribute("email","อีเมล์"), attribute("pwd","รหัสผ่าน"));
    }
}
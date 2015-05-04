package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class CustomerRequest extends RequestValidation {

    public CustomerRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(
                attribute("fname", "ชื่อ"),
                attribute("lname", "นามสกุล"),
                attribute("sex", "เพศ"),
                attribute("dob", "วันเดือนปีเกิด"),
                attribute("phone", "เบอร์โทรศัพท์"),
                attribute("email", "อีเมลล์"),
                attribute("vat_id", "เลขประจำตัวผู้เสียภาษี"),
                attribute("pwd", "รหัสผ่าน"));
    }
}


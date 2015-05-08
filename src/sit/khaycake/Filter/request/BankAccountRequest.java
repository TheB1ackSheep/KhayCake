package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/7/2015.
 */
public class BankAccountRequest extends RequestValidation {

    public BankAccountRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(attribute("acc_name","ชื่อบัญชี"), attribute("acc_no","เลขที่บัญชี"), integerAttribute("type_id","ประเภทบัญชี")
        , integerAttribute("branch_id","ธนาคาร"));
    }
}
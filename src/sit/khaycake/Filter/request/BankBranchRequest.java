package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class BankBranchRequest extends RequestValidation {

    public BankBranchRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(attribute("name_th", "ชื่อธนาคารภาษาไทย"),
                attribute("name_en", "ชื่อธนาคารภาษาอังกฤษ"),
                integerAttribute("pic_id","รหัสรูปภาพ",false));
    }
}

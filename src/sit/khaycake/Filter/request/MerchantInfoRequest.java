package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class MerchantInfoRequest extends RequestValidation {

    public MerchantInfoRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(
                attribute("name", "ชื่อผู้ขาย"),
                attribute("vat_id", "เลขประจำตัวผู้เสียภาษี"),
                attribute("phone", "เบอร์โทรศัพท์"),
                attribute("fax", "เบอร์โทรสาร"),
                floatAttribute("vat_value", "มูลค่าภาษี"),
                integerAttribute("addr_id", "รหัสที่อยู่"));
    }
}

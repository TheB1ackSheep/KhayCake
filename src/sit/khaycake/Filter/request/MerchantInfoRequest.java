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
                attribute("date", "วันที่"),
                attribute("qrand_total", "Qrand total"),
                attribute("sub_total", "จำนวนทั้งหมด"),
                attribute("vat", "ภาษีมูลค่าเพิ่ม"),
                attribute("qrand_total_text", "Qrand total text"),
                attribute("patm_id", "รหัสการชำระเงิน"),
                attribute("meif_id", "รหัสผู้ขาย"));
    }
}

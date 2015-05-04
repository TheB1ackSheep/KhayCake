package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class InvoiceRequest extends RequestValidation {

    public InvoiceRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(
                attribute("date", "วันที่"),
                floatAttribute("qrand_total", "Qrand total"),
                floatAttribute("sub_total", "จำนวนทั้งหมด"),
                floatAttribute("vat", "ภาษีมูลค่าเพิ่ม"),
                attribute("qrand_total_text", "Qrand total text"),
                integerAttribute("patm_id", "รหัสการชำระเงิน"),
                integerAttribute("meif_id", "รหัสผู้ขาย"));
    }
}

package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class PaymentRequest extends RequestValidation {

    public PaymentRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(
                integerAttribute("order_id", "รหัสคำสั่งซื้อ"),
                floatAttribute("amount", "จำนวน"),
                integerAttribute("baac_id", "รหัสบัญชี"),
                attribute("date_time", "วันเวลา"),
                integerAttribute("past_id", "รหัสการชำระเงิน"));
    }
}
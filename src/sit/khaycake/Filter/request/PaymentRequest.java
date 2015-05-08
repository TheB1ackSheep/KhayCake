package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/7/2015.
 */
public class PaymentRequest extends RequestValidation {

    public PaymentRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(integerAttribute("order_id","เลขที่สั่งซื้อ"), integerAttribute("bankacc_id","บัญชีธนาคารที่โอน"),
                floatAttribute("amount","จำนวนเงินที่โอน"),dateAttribute("date","วันที่โอน"));

    }


}

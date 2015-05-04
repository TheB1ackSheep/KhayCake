package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class OrderRequest extends RequestValidation {

    public OrderRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(
                integerAttribute("cust_id", "รหัสลูกค้า"),
                integerAttribute("order_date", "วันที่สั่งซื้อ"),
                integerAttribute("orst_id", "รหัสสถานะการสั่งซื้อ"),
                integerAttribute("shme_id", "รหัสประเภทการจัดส่ง"),
                integerAttribute("shtr_id", "Shtr id",false),
                floatAttribute("total_price", "จำนวนเงินทั้งหมด"),
                integerAttribute("total_qty", "จำนวนทั้งหมด(ชิ้น)"));
    }
}

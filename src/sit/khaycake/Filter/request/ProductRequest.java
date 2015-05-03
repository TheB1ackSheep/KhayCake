package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Pasuth on 29/4/2558.
 */
public class ProductRequest extends RequestValidation {

    public ProductRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(attribute("name", "ชื่อเค้ก"), attribute("detail", "รายละเอียด"),
                integerAttribute("unit_id", "หน่วยเค้ก"), integerAttribute("cat_id", "ชนิดเค้ก"),
                integerAttribute("pic_id", "รูปภาพ"),
                floatAttribute("cost", "ราคาต้นทุน"), floatAttribute("price", "ราคาขาย"),
                integerAttribute("sale_qty", "จำนวนขาย", false),
                integerAttribute("sale_price", "ราคาขาย", false));
    }
}

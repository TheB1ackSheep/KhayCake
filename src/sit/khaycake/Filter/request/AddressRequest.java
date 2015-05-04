package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class AddressRequest extends RequestValidation {

    public AddressRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(integerAttribute("p_id", "เลขที่สินค้า"),
                integerAttribute("qty", "จำนวนสินค้า"));
    }
}
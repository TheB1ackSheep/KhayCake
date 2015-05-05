package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/6/2015.
 */
public class ShipmentAddressRequest extends RequestValidation {

    public ShipmentAddressRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(integerAttribute("shad_id","เลขที่"), attribute("fname", "ชื่อจริง",false), attribute("lname","นามสกุล",false), attribute("address", "ที่อยู่",false)
            , integerAttribute("tumb_id","ตำบล/แขวง",false));
    }
}
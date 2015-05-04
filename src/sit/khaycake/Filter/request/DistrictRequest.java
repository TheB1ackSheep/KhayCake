package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class DistrictRequest extends RequestValidation {

    public DistrictRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(attribute("name", "ชื่ออำเภอ"),
                integerAttribute("prov_id", "รหัสจังหวัด"));
    }
}

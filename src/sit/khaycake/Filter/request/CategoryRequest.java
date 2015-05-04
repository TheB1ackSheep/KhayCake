package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/4/2015.
 */
public class CategoryRequest extends RequestValidation {

    public CategoryRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(attribute("addr_no", "เลขที่บ้าน"),
                attribute("addr_add", "ที่อยู่"), attribute("street", "ถนน"),
                attribute("sudt_id", "รหัสตำบล"));
    }
}

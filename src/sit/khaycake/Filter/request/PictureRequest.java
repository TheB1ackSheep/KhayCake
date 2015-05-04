package sit.khaycake.Filter.request;

import sit.khaycake.Filter.RequestValidation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Pasuth on 29/4/2558.
 */
public class PictureRequest extends RequestValidation {

    public PictureRequest(HttpServletRequest request) {
        super(request);
    }

    public boolean validate() throws IOException {
        return this.validate(fileAttribute("pictures","รูปภาพ"));
    }
}

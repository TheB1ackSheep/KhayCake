package sit.khaycake.Filter;

/**
 * Created by Falook Glico on 4/26/2015.
 */

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by INT303 on 10/4/2558.
 */

public class RequestValidation extends HttpServletRequestWrapper {



    public static FormAttribute attribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, false, false, false);
    }

    public static FormAttribute integerAttribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, true, false, false);
    }

    public static FormAttribute integerAttribute(String name, String nickname, boolean required) {
        return new FormAttribute(name, nickname, required, -1, -1, true, false, false);
    }

    public static FormAttribute floatAttribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, false, true, false);
    }

    public static FormAttribute emailAttribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, false, false, true);
    }


    public RequestValidation(HttpServletRequest request) {
        super(request);
    }

    public boolean validate(FormAttribute... required) throws IOException {
        ErrorMessage error = new ErrorMessage(this.getSession());
        List<String> errors = new ArrayList<>();
        for (FormAttribute r : required) {
            String param = this.getRequest().getParameter(r.getName());
            if (r.isRequired()) {
                if (param == null || param.length() == 0)
                    errors.add("คุณไม่ได้ระบุ " + r);
            }

               if (param != null) {
                    if (r.isInteger() && !Util.isInteger(param))
                        errors.add(r + " จะต้องเป็นตัวเลขเท่านั้น");
                    if (r.isFloat() && !Util.isFloat(param))
                        errors.add(r + " จะต้องเป็นตัวเลขทศนิยมเท่านั้น");
                    if (r.isEmail() && !Util.isEmail(param))
                        errors.add("รูปแบบอีเมล์ไม่ถูกต้อง");
                }


        }
        if (!errors.isEmpty()) {
            error.setMessage(errors);
            return false;
        }
        return true;
    }


}

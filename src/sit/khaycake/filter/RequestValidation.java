package sit.khaycake.filter;

/**
 * Created by Falook Glico on 4/26/2015.
 */
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import sit.khaycake.exception.ValidationException;
import sit.khaycake.util.ErrorHandler;
import sit.khaycake.util.Util;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by INT303 on 10/4/2558.
 */

public class RequestValidation extends HttpServletRequestWrapper {

    public RequestValidation(HttpServletRequest request){
        super(request);
    }

    public boolean validate(FormAttribute... required) {
        ErrorHandler errors = new ErrorHandler(this.getSession());
        for(FormAttribute r : required){
            String param = this.getRequest().getParameter(r.getName());
            if(r.isRequired()){
                if(param == null || param.length() == 0)
                    errors.addException(new ValidationException("คุณไม่ได้ระบุ " + r));
            }else{
                if(param != null){
                    if(r.isInteger() && !Util.isInteger(param))
                        errors.addException(new ValidationException(r + " จะต้องเป็นตัวเลขเท่านั้น"));
                    if(r.isFloat() && !Util.isFloat(param))
                        errors.addException(new ValidationException(r + " จะต้องเป็นตัวเลขทศนิยมเท่านั้น"));
                }
            }

        }
        if(errors.getExceptions() != null && errors.getExceptions().size() > 0)
            return false;
        return true;
    }

    public boolean validateMultipart(FormAttribute... required){
        ErrorHandler errors = new ErrorHandler(this.getSession());

        HttpServletRequest req = (HttpServletRequest)this.getRequest();
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        if(isMultipart){

            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Configure a repository (to ensure a secure temp location is used)
            ServletContext servletContext = req.getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List<FileItem> items = upload.parseRequest(req);
                for(FileItem item : items){
                    for(FormAttribute r : required){
                        if(item.getFieldName().equals(r.getName())) {
                            if (item.isFormField()) {
                                if (r.isRequired() && item.getString().length() == 0)
                                    errors.addException(new ValidationException("คุณไม่ได้ใส่ " + r));

                            } else {
                                if (r.isRequired() && item.getName().length() == 0)
                                    errors.addException(new ValidationException("คุณไม่ได้ใส่ " + r));
                            }
                        }

                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

        }
        if(errors.getExceptions() != null && errors.getExceptions().size() > 0)
            return false;
        return true;
    }

}

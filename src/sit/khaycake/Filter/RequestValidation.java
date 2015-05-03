package sit.khaycake.Filter;

/**
 * Created by Falook Glico on 4/26/2015.
 */
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.Util;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by INT303 on 10/4/2558.
 */

public class RequestValidation extends HttpServletRequestWrapper {
    private HttpServletResponse response;
    public RequestValidation(HttpServletRequest request, HttpServletResponse response){
        super(request);
        this.response = response;
    }

    public boolean validate(FormAttribute... required) throws IOException {
        ErrorMessage error = new ErrorMessage(this.getSession());
        List<String> errors = new ArrayList<>();
        for(FormAttribute r : required){
            String param = this.getRequest().getParameter(r.getName());
            if(r.isRequired()){
                if(param == null || param.length() == 0)
                    errors.add("คุณไม่ได้ระบุ " + r);
            }else{
                if(param != null){
                    if(r.isInteger() && !Util.isInteger(param))
                        errors.add(r + " จะต้องเป็นตัวเลขเท่านั้น");
                    if(r.isFloat() && !Util.isFloat(param))
                        errors.add(r + " จะต้องเป็นตัวเลขทศนิยมเท่านั้น");
                }
            }

        }
        if(!errors.isEmpty()) {
            error.setMessage(errors);
            return false;
        }
        return true;
    }

    public boolean validateMultipart(FormAttribute... required){
        ErrorMessage error = new ErrorMessage(this.getSession());
        List<String> errors = new ArrayList<>();

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
                                    errors.add("คุณไม่ได้ใส่ " + r);

                            } else {
                                if (r.isRequired() && item.getName().length() == 0)
                                    errors.add("คุณไม่ได้ใส่ " + r);
                            }
                        }

                    }
                }
            } catch (FileUploadException e) {
                errors.add(e.getMessage());
            }

        }
        if(!errors.isEmpty()) {
            error.setMessage(errors);
            return false;
        }
        return true;
    }

}

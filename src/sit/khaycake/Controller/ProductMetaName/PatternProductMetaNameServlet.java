package sit.khaycake.Controller.ProductMetaName;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductMetaName;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductMetaNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 1)+1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                ProductMetaName productMetaName = (ProductMetaName)SQL.findById(ProductMetaName.class,resource);

                if (productMetaName!=null) {
                    ProductMetaName.delete(productMetaName.getId());
                    success.setMessage(productMetaName);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else {
            try {
                ProductMetaName productMetaName = (ProductMetaName) SQL.findById(ProductMetaName.class, Integer.parseInt(resource));
                if (productMetaName != null) {
                    success.setMessage(productMetaName);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 1)+1);
        try {
            ProductMetaName productMetaName = (ProductMetaName) SQL.findById(ProductMetaName.class, Integer.parseInt(resource));
            if (productMetaName != null) {
                productMetaName.setName(request.getParameter("NAME"));
                productMetaName.update();

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }


    }

}

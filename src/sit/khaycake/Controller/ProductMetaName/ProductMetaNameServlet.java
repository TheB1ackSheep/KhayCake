package sit.khaycake.Controller.ProductMetaName;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.ProductMetaNameRequest;
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
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductMetaNameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            success.setMessage((ProductMetaName) SQL.findAll(ProductMetaName.class));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        ProductMetaNameRequest productMetaNameRequest
                = new ProductMetaNameRequest(request);
        if(productMetaNameRequest.validate()) {
            try {
                ProductMetaName productMetaName = new ProductMetaName();
                productMetaName.setName("name");


                success.setMessage(productMetaName);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

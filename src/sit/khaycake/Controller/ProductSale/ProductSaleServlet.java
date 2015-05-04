package sit.khaycake.Controller.ProductSale;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.ProductSaleRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductSale;
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
public class ProductSaleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            success.setMessage((ProductSale) SQL.findAll(ProductSale.class));
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
        ProductSaleRequest productSaleRequest = new ProductSaleRequest(request);
        if(productSaleRequest.validate()) {
            try {
                ProductSale productSale = new ProductSale();
                productSale.setProdId(Integer.parseInt(request.getParameter("prod_id")));
                productSale.setQty(Integer.parseInt(request.getParameter("qty")));
                productSale.save();
                success.setMessage(productSale);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

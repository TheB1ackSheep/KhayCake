package sit.khaycake.Controller.ProductSale;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.ProductSaleRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Product;
import sit.khaycake.model.ProductSale;
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
public class PatternProductSaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                ProductSale productSale = (ProductSale) SQL.findById(ProductSale.class, resource);
                if (productSale != null) {
                    ProductSale.delete(productSale.getId());
                    success.setMessage(productSale);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }catch (Exception ex){
                error.setMessage(ex.getMessage());
            }
        } else {
            ProductSale productSale = null;
            try {
                productSale = (ProductSale) SQL.findById(ProductSale.class, Integer.parseInt(resource));
                if (productSale != null) {
                    success.setMessage(productSale);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
       HttpSession session = request.getSession();
       SuccessMessage success = new SuccessMessage(session);
       ErrorMessage error = new ErrorMessage(session);
       ProductSaleRequest productSaleRequest = new ProductSaleRequest(request);
       if(productSaleRequest.validate()) {
           try {
               ProductSale productSale = (ProductSale) SQL.findById(ProductSale.class, Integer.parseInt(resource));
               if (productSale != null) {
                   productSale.setProdId(Integer.parseInt(request.getParameter("prod_id")));
                   productSale.setQty(Integer.parseInt(request.getParameter("qty")));
                   productSale.update();
                   success.setMessage(productSale);
               } else {
                   response.sendError(HttpServletResponse.SC_NOT_FOUND);
               }
           } catch (Exception ex) {
               error.setMessage(ex.getMessage());
           }
       }

    }

}

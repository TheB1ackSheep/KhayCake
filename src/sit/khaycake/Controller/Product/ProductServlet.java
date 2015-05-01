package sit.khaycake.Controller.Product;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Product;
import sit.khaycake.model.ProductSale;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.JsonMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            List<Product> products = (List<Product>)SQL.findAll(Product.class);
            success.setMessage(products);
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
        try {
            SQL sql = new SQL();
            Product product = new Product();
            product.setName(request.getParameter("NAME"));
            product.setDetail(request.getParameter("DETAIL"));
            product.setCost(Double.parseDouble(request.getParameter("COST")));
            product.setCategory((Category) SQL.findById(Category.class
                    , Integer.parseInt(request.getParameter("CAT_ID"))));
            product.save();

            String[] productSales = request.getParameterValues("QTY");
            for (String productSaleStr : productSales) {
                ProductSale productSale = new ProductSale();
                productSale.setProdId(product.getId());
                productSale.setQty(Integer.parseInt(productSaleStr));
                productSale.save();
            }
            success.setMessage(product);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
    }
}

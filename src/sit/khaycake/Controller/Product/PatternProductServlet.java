package sit.khaycake.Controller.Product;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Product;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = Product.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            Gson gson = new Gson();


            try {
                if(Util.isInteger(resource)) {
                    Product product = null;
                    product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));
                    if (product != null) {

                        response.getWriter().print(gson.toJson(product));
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }else {
                    List<Product> products = new ArrayList<>();
                    products = (List<Product>) SQL.findByKeyword(Product.class, resource);
                    if (!products.isEmpty()) {

                        response.getWriter().print(gson.toJson(products));
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Product product = null;
        try {
            product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (product != null) {
            try{
                product.setName(request.getParameter("NAME"));
                product.setDetail(request.getParameter("DETAIL"));
                product.setCost(Double.parseDouble(request.getParameter("COST")));
                product.setCategory(
                        (Category)SQL.findById(Category.class, Integer.parseInt(request.getParameter("CAT_ID"))));
                product.update();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

package sit.khaycake.Controller.Product;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(Product.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Product product = new Product();
            product.setName(request.getParameter("NAME"));
            product.setDetail(request.getParameter("DETAIL"));
            product.setCost(Double.parseDouble(request.getParameter("COST")));
            product.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(product));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }*/
}

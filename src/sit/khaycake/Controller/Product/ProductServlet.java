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
            List products = SQL.findAll(Product.class);
            Gson gson = new Gson();
            String result = gson.toJson(products, Product.class);
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Product product = new Product();
            product.setName(request.getParameter("name"));
            product.setDetail(request.getParameter("detail"));
            product.setCost(Double.parseDouble(request.getParameter("cost")));


            int addId = sql
                    .insert()
                    .into(Product.TABLE_NAME, Product.COLUMN_NAME, Product.COLUMN_DETAIL, Product.COLUMN_COST)
                    .values(product.getName(), product.getDetail(), product.getCost())
                    .exec();
            sql.clear();
            product.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(product));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

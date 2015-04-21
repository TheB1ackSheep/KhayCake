package sit.khaycake.Controller.Product;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(Product.TABLE_NAME)
                        .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            Product product = null;
            try {
                product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (product != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(product));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
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
            product.setName(request.getParameter("name"));
            product.setDetail(request.getParameter("detail"));
            product.setCost(Double.parseDouble(request.getParameter("cost")));


            SQL sql = new SQL();
            try {
                sql
                        .update(Product.TABLE_NAME)
                        .set(Product.COLUMN_NAME, product.getName())
                        .set(Product.COLUMN_DETAIL, product.getDetail())
                        .set(Product.COLUMN_COST, product.getCost())
                        .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, product.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

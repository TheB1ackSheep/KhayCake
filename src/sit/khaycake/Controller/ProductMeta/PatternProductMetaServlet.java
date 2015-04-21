package sit.khaycake.Controller.ProductMeta;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.OrderStatus;
import sit.khaycake.model.ProductMeta;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductMetaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(ProductMeta.TABLE_NAME)
                        .where(ProductMeta.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            ProductMeta productMeta = null;
            try {
                productMeta = (ProductMeta) SQL.findById(ProductMeta.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (productMeta != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(productMeta));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        ProductMeta productMeta = null;
        try {
            productMeta = (ProductMeta) SQL.findById(ProductMeta.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (productMeta != null) {
            productMeta.setPrmnId(Integer.parseInt("prmnId"));

            SQL sql = new SQL();
            try {
                sql
                        .update(ProductMeta.TABLE_NAME)
                        .set(ProductMeta.COLUMN_PRMN_ID, productMeta.getPrmnId())
                        .where(OrderStatus.COLUMN_ID, SQL.WhereClause.Operator.EQ, productMeta.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

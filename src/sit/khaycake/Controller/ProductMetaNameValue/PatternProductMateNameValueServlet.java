package sit.khaycake.Controller.ProductMetaNameValue;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductMetaName;
import sit.khaycake.model.ProductMetaNameValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductMateNameValueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(ProductMetaNameValue.TABLE_NAME)
                        .where(ProductMetaNameValue.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            ProductMetaNameValue productMetaNameValue = null;
            try {
                productMetaNameValue = (ProductMetaNameValue) SQL.findById(ProductMetaNameValue.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (productMetaNameValue != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(productMetaNameValue));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        ProductMetaNameValue productMetaNameValue = null;
        try {
            productMetaNameValue = (ProductMetaNameValue) SQL.findById(ProductMetaNameValue.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (productMetaNameValue != null) {
            productMetaNameValue.setPrmnId(Integer.parseInt(request.getParameter("prmnId")));
            productMetaNameValue.setPrice(Double.parseDouble(request.getParameter("price")));
            productMetaNameValue.setValue(request.getParameter("value"));


            SQL sql = new SQL();
            try {
                sql
                        .update(ProductMetaNameValue.TABLE_NAME)
                        .set(ProductMetaNameValue.COLUMN_PRMN_ID, productMetaNameValue.getPrmnId())
                        .set(ProductMetaNameValue.COLUMN_PRICE, productMetaNameValue.getPrice())
                        .set(ProductMetaNameValue.COLUMN_VALUE, productMetaNameValue.getValue())
                        .where(ProductMetaName.COLUMN_ID, SQL.WhereClause.Operator.EQ, productMetaNameValue.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

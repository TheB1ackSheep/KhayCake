package sit.khaycake.Controller.ProductSale;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductSale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductSaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(ProductSale.TABLE_NAME)
                        .where(ProductSale.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            ProductSale productSale = null;
            try {
                productSale = (ProductSale) SQL.findById(ProductSale.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (productSale != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(productSale));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        ProductSale productSale = null;
        try {
            productSale = (ProductSale) SQL.findById(ProductSale.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (productSale != null) {
            productSale.setPriceN(Double.parseDouble(request.getParameter("priceN")));
            productSale.setPriceV(Double.parseDouble(request.getParameter("priceV")));
            productSale.setProdId(Integer.parseInt(request.getParameter("prodId")));
            productSale.setQty(Integer.parseInt(request.getParameter("qty")));
            productSale.setUnitId(Integer.parseInt(request.getParameter("unitId")));


            SQL sql = new SQL();
            try {
                sql
                        .update(ProductSale.TABLE_NAME)
                        .set(ProductSale.COLUMN_PRICE_N, productSale.getPriceN())
                        .set(ProductSale.COLUMN_PRICE_V, productSale.getPriceV())
                        .set(ProductSale.COLUMN_PROD_ID, productSale.getProdId())
                        .set(ProductSale.COLUMN_QTY, productSale.getQty())
                        .set(ProductSale.COLUMN_UNIT_ID, productSale.getUnitId())
                        .where(ProductSale.COLUMN_ID, SQL.WhereClause.Operator.EQ, productSale.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

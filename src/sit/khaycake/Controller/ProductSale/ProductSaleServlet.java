package sit.khaycake.Controller.ProductSale;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductSale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductSaleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List productSales = SQL.findAll(ProductSale.class);
            Gson gson = new Gson();
            String result = gson.toJson(productSales, ProductSale.class);
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
            ProductSale productSale = new ProductSale();
            productSale.setPriceN(Double.parseDouble(request.getParameter("priceN")));
            productSale.setPriceV(Double.parseDouble(request.getParameter("priceV")));
            productSale.setProdId(Integer.parseInt(request.getParameter("prodId")));
            productSale.setQty(Integer.parseInt(request.getParameter("qty")));
            productSale.setUnitId(Integer.parseInt(request.getParameter("unitId")));


            int addId = sql
                    .insert()
                    .into(ProductSale.TABLE_NAME, ProductSale.COLUMN_PRICE_N, ProductSale.COLUMN_PRICE_V, ProductSale.COLUMN_PROD_ID,
                            ProductSale.COLUMN_QTY, ProductSale.COLUMN_UNIT_ID)
                    .values(productSale.getPriceN(), productSale.getPriceV(), productSale.getProdId(), productSale.getQty(), productSale.getUnitId())
                    .exec();
            sql.clear();
            productSale.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(productSale));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

package sit.khaycake.Controller.ProductMetaNameValue;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductMetaNameValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductMetaNameValueServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List productMetaNameValues = SQL.findAll(ProductMetaNameValue.class);
            Gson gson = new Gson();
            String result = gson.toJson(productMetaNameValues, ProductMetaNameValue.class);
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
            ProductMetaNameValue productMetaNameValue = new ProductMetaNameValue();
            productMetaNameValue.setPrmnId(Integer.parseInt(request.getParameter("prmnId")));
            productMetaNameValue.setPrice(Double.parseDouble(request.getParameter("price")));
            productMetaNameValue.setValue(request.getParameter("value"));

            int id = sql
                    .insert()
                    .into(ProductMetaNameValue.TABLE_NAME, ProductMetaNameValue.COLUMN_PRMN_ID, ProductMetaNameValue.COLUMN_PRICE,
                            ProductMetaNameValue.COLUMN_VALUE)
                    .values(productMetaNameValue.getPrmnId(), productMetaNameValue.getPrice(), productMetaNameValue.getValue())
                    .exec();
            sql.clear();
            productMetaNameValue.setId(id);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(productMetaNameValue));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/
}

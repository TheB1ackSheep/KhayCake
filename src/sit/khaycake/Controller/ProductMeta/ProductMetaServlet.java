package sit.khaycake.Controller.ProductMeta;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductMeta;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductMetaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List productMetas = SQL.findAll(ProductMeta.class);
            Gson gson = new Gson();
            String result = gson.toJson(productMetas, ProductMeta.class);
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
            ProductMeta productMeta = new ProductMeta();
            productMeta.setId(Integer.parseInt("prodId"));
            productMeta.setPrmnId(Integer.parseInt("prmnId"));

            sql
                    .insert()
                    .into(ProductMeta.TABLE_NAME, ProductMeta.COLUMN_PROD_ID, ProductMeta.COLUMN_PRMN_ID)
                    .values(productMeta.getId(), productMeta.getPrmnId())
                    .exec();
            sql.clear();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(productMeta));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/
}

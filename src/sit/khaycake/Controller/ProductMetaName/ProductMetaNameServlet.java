package sit.khaycake.Controller.ProductMetaName;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ProductMetaName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductMetaNameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List productMetaNames = SQL.findAll(ProductMetaName.class);
            Gson gson = new Gson();
            String result = gson.toJson(productMetaNames, ProductMetaName.class);
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
            ProductMetaName productMetaName = new ProductMetaName();
            productMetaName.setName("name");

            sql
                    .insert()
                    .into(ProductMetaName.TABLE_NAME, ProductMetaName.COLUMN_NAME)
                    .values(productMetaName.getName())
                    .exec();
            sql.clear();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(productMetaName));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

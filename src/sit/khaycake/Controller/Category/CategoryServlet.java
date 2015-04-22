package sit.khaycake.Controller.Category;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 22/4/2558.
 */
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List customers = SQL.findAll(Category.class);
            Gson gson = new Gson();
            String result = gson.toJson(customers, Category.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }
}

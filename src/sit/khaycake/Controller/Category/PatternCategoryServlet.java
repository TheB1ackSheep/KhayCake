package sit.khaycake.Controller.Category;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Customer;
import sit.khaycake.model.Product;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.Encryption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);


        if (resource.indexOf("product") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            List<Product> product = new ArrayList<>();
            try {
                product = (List<Product>)SQL.findByKeyword(Product.class,resource);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            if (!product.isEmpty()) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(product));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            Category category = null;
            try {
                category = (Category) SQL.findById(Category.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            if (category != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(category));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        Category category = null;
        try {
            category = (Category) SQL.findById(Category.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        if (category != null) {
            try {
                category.setName(request.getParameter("NAME"));
                category.setCatParentId(Integer.parseInt(request.getParameter("CAT_PARENT_ID")));
                category.update();
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

package sit.khaycake.Controller.Category;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.CategoryRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Customer;
import sit.khaycake.model.Product;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.Encryption;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);


        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Category category = (Category)SQL.findById(Category.class,resource);
                if (category != null) {
                    Category.delete(Integer.parseInt(resource));
                    succes.setMessage(category);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }else if (resource.indexOf("product") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                List<Product> product = Product.findByCategory(Integer.parseInt(resource));
                if (!product.isEmpty()) {
                    succes.setMessage(product);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }


        } else {

            try {
                Category category = (Category) SQL.findById(Category.class, Integer.parseInt(resource));
                if (category != null) {
                    succes.setMessage(category);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        CategoryRequest categoryRequest = new CategoryRequest(request);
        if(categoryRequest.validate()) {
            try {
                Category category = (Category) SQL.findById(Category.class, Integer.parseInt(resource));
                if (category != null) {
                    category.setName(request.getParameter("name"));
                    category.setCatParentId(Integer.parseInt(request.getParameter("cat_parent_id")));
                    category.update();
                    succes.setMessage(category);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }

}

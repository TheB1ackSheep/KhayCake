package sit.khaycake.Controller.Category;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 22/4/2558.
 */
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((Category) SQL.findAll(Category.class));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Category category = new Category();
            category.setName(request.getParameter("NAME"));
            category.setCatParentId(Integer.parseInt(request.getParameter("CAT_PARENT_ID")));
            category.save();

            succes.setMessage(category);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

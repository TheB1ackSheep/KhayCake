package sit.khaycake.controller.category;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Product;
import sit.khaycake.util.ErrorHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Falook Glico on 4/26/2015.
 */
@WebServlet(name = "CategoryController", urlPatterns = "/category/*")
public class CategoryController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String[] urlFragment = request.getRequestURI().split("/");

        String id = null,product = null;

        if(urlFragment.length > 3)
            id = urlFragment[3];
        if(urlFragment.length > 4)
            product = urlFragment[4];

        try {

            if (id != null) {
                Category category = (Category) SQL.findById(Category.class, id);
                if (category == null)
                    response.sendError(404);

                if (product != null && product.equals("product"))
                    request.setAttribute("print",Category.getProductList(id));
                else
                    request.setAttribute("print",category);
            } else
                request.setAttribute("print",SQL.findAll(Category.class));

        } catch (Exception e) {
            ErrorHandler error = new ErrorHandler(request.getSession());
            error.addException(e);
        }
    }
}

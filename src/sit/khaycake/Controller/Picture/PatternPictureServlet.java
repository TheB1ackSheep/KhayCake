package sit.khaycake.Controller.Picture;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Picture;
import sit.khaycake.model.Product;
import sit.khaycake.util.Util;

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
public class PatternPictureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = Picture.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            Gson gson = new Gson();
            try {
                    Picture picture = null;
                    picture = (Picture) SQL.findById(Picture.class, Integer.parseInt(resource));
                    if (picture != null) {

                        response.getWriter().print(gson.toJson(picture));
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Product product = null;
        try {
            product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (product != null) {
            try{
                product.setName(request.getParameter("NAME"));
                product.setDetail(request.getParameter("DETAIL"));
                product.setCost(Double.parseDouble(request.getParameter("COST")));
                product.setCategory(
                        (Category)SQL.findById(Category.class, Integer.parseInt(request.getParameter("CAT_ID"))));
                product.update();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

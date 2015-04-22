package sit.khaycake.Controller.Picture;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Picture;
import sit.khaycake.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PictureServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List pictures = SQL.findAll(Picture.class);
            Gson gson = new Gson();
            String result = gson.toJson(pictures, Picture.class);
            response.setContentType("application/json");// ทำ filter ด้วย
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
            Picture picture = new Picture();
            picture.setPath(request.getParameter("PATH"));
            picture.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(picture));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }*/
}

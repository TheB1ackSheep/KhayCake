package sit.khaycake.Controller.District;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.District;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class DistrictServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(District.class));
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            District district = new District();
            district.setName(request.getParameter("name"));
            district.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(district));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }*/
}

package sit.khaycake.Controller.SubDistrict;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.SubDistrict;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class SubDistrictServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List subDistricts = SQL.findAll(SubDistrict.class);
            Gson gson = new Gson();
            String result = gson.toJson(subDistricts, SubDistrict.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            SubDistrict subDistrict = new SubDistrict();
            subDistrict.setName(request.getParameter("name"));
            subDistrict.setZipCode(request.getParameter("zipCode"));

            int addId = sql
                    .insert()
                    .into(SubDistrict.TABLE_NAME, SubDistrict.COLUMN_NAME, SubDistrict.COLUMN_DIST_ID)
                    .values(subDistrict.getName(), Integer.parseInt(request.getParameter("subDistrictId")))
                    .exec();
            sql.clear();
            subDistrict.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(subDistrict));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }
}

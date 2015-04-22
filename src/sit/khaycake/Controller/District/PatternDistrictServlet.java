package sit.khaycake.Controller.District;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.District;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternDistrictServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(District.TABLE_NAME)
                        .where(District.COLUMN_DIST_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            District district = null;
            try {
                district = (District) SQL.findById(District.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (district != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(district));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        District district = null;
        try {
            district = (District) SQL.findById(District.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (district != null) {
            district.setName(request.getParameter("name"));

            SQL sql = new SQL();
            try {
                sql
                        .update(District.TABLE_NAME)
                        .set(District.COLUMN_NAME, district.getName())
                        .set(District.COLUMN_PROV_ID, request.getParameter("provinceId"))
                        .where(District.COLUMN_DIST_ID, SQL.WhereClause.Operator.EQ, district.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

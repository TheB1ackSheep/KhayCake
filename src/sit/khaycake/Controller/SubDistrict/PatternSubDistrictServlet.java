package sit.khaycake.Controller.SubDistrict;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.District;
import sit.khaycake.model.SubDistrict;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternSubDistrictServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(SubDistrict.TABLE_NAME)
                        .where(SubDistrict.COLUMN_DIST_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            SubDistrict subDistrict = null;
            try {
                subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (subDistrict != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(subDistrict));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        SubDistrict subDistrict = null;
        try {
            subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (subDistrict != null) {
            subDistrict.setName(request.getParameter("name"));
            subDistrict.setZipCode(request.getParameter("zipCode"));

            SQL sql = new SQL();
            try {
                sql
                        .update(SubDistrict.TABLE_NAME)
                        .set(SubDistrict.COLUMN_NAME, subDistrict.getName())
                        .set(SubDistrict.COLUMN_ZIPCODE, subDistrict.getName())
                        .set(SubDistrict.COLUMN_DIST_ID, request.getParameter("districtId"))
                        .where(District.COLUMN_DIST_ID, SQL.WhereClause.Operator.EQ, subDistrict.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

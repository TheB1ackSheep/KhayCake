package sit.khaycake.Controller.Unit;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Unit;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternUnitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(Unit.TABLE_NAME)
                        .where(Unit.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            try {
                Unit unit = (Unit) SQL.findById(Unit.class, Integer.parseInt(resource));
                if (unit == null) {
                    response.sendError(404);
                }
                success.setMessage(unit);
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Unit unit = null;
        try {
            unit = (Unit) SQL.findById(Unit.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (unit != null) {
            unit.setName(request.getParameter("name"));


            SQL sql = new SQL();
            try {
                sql
                        .update(Unit.TABLE_NAME)
                        .set(Unit.COLUMN_NAME, unit.getName())
                        .where(Unit.COLUMN_ID, SQL.WhereClause.Operator.EQ, unit.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

package sit.khaycake.Controller.Unit;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.UnitRequest;
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
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Unit unit = (Unit)SQL.findById(Unit.class,resource);

                if (unit!=null) {
                    Unit.delete(unit.getId());
                    success.setMessage(unit);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            try {
                Unit unit = (Unit) SQL.findById(Unit.class, Integer.parseInt(resource));
                if (unit == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                success.setMessage(unit);
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        UnitRequest unitRequest = new UnitRequest(request);
        if(unitRequest.validate()) {
            try {
                Unit unit = (Unit) SQL.findById(Unit.class, Integer.parseInt(resource));
                if (unit != null) {
                    unit.setName(request.getParameter("name"));
                    unit.update();
                    success.setMessage(unit);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }
        }

    }

}

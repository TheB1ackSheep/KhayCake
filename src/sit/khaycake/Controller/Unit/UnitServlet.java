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
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class UnitServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {
            List<Unit> units = (List<Unit>)SQL.findAll(Unit.class);
            success.setMessage(units);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        UnitRequest unitRequest = new UnitRequest(request);
        if(unitRequest.validate()) {
            try {
                Unit unit = new Unit();
                unit.setName(request.getParameter("name"));
                unit.save();

                success.setMessage(unit);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

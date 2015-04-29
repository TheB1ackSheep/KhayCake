package sit.khaycake.controller.unit;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Unit;
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
@WebServlet(name = "UnitController", urlPatterns = "/unit/*")
public class UnitController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String[] urlFragment = request.getRequestURI().split("/");

        String id = null;

        if (urlFragment.length > 3)
            id = urlFragment[3];

        try {

            if (id != null) {
                Unit unit = (Unit) SQL.findById(Unit.class, id);
                if (unit == null)
                    response.sendError(404);
                else
                    request.setAttribute("print",unit);

            } else
                request.setAttribute("print", SQL.findAll(Unit.class));

        } catch (Exception e) {
            ErrorHandler error = new ErrorHandler(request.getSession());
            error.addException(e);
        }
    }
}

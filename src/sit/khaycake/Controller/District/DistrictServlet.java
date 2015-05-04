package sit.khaycake.Controller.District;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.District;
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
public class DistrictServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((District) SQL.findAll(District.class));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            District district = new District();
            district.setName(request.getParameter("NAME"));
            district.setProvinceId(Integer.parseInt(request.getParameter("PROV_ID")));
            district.save();
            succes.setMessage(district);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

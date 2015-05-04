package sit.khaycake.Controller.SubDistrict;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.SubDistrict;
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
public class SubDistrictServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((SubDistrict) SQL.findAll(SubDistrict.class));
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
            SubDistrict subDistrict = new SubDistrict();
            subDistrict.setName(request.getParameter("NAME"));
            subDistrict.setZipCode(request.getParameter("ZIPCODE"));
            subDistrict.setDistrictId(Integer.parseInt(request.getParameter("DIST_ID")));
            succes.setMessage(subDistrict);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

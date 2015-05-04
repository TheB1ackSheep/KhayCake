package sit.khaycake.Controller.Address;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
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
public class AddressServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Gson gson = new Gson();
            success.setMessage(gson.toJson(SQL.findAll(Address.class)));
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
        try {
            Address address = new Address();
            address.setAddrNo(request.getParameter("ADDR_NO"));
            address.setAddrAdd(request.getParameter("ADDR_ADD"));
            address.setStreet(request.getParameter("STREET"));
            address.setSubDistrictId(Integer.parseInt(request.getParameter("SUDT_ID")));
            address.save();

            Gson gson = new Gson();
            success.setMessage(gson.toJson(address));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

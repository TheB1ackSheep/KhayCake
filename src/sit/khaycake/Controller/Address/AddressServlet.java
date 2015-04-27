package sit.khaycake.Controller.Address;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
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
public class AddressServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(SQL.findAll(Address.class)));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Address address = new Address();
            address.setAddrNo(request.getParameter("ADDR_NO"));
            address.setAddrAdd(request.getParameter("ADDR_ADD"));
            address.setStreet(request.getParameter("STREET"));
            address.setSubDistrict((SubDistrict)
                    SQL.findById(SubDistrict.class, request.getParameter("SUDT_ID")));
            address.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(address));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}

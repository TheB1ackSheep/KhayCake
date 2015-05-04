package sit.khaycake.Controller.Address;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
import sit.khaycake.model.District;
import sit.khaycake.model.SubDistrict;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternAddressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                int result = Address.delete(Integer.parseInt(resource));
                if (result < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else if (resource.indexOf("subdistrict") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, resource);
                    if (subDistrict == null)
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(subDistrict);
                }

            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else if (resource.indexOf("district") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    SubDistrict subDistrict = SQL.findById(SubDistrict.class, resource);
                    District district = SQL.findById(District.class, subDistrict.getDistrict());
                    if (district == null)
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(district);
                }

            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else if (resource.indexOf("province") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, resource);
                    if (subDistrict == null)
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(subDistrict);
                }

            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else {
            Address address = null;
            try {
                if (Util.isInteger(resource)) {
                    address = (Address) SQL.findById(Address.class, Integer.parseInt(resource));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
            if (address != null) {
                Gson gson = new Gson();
                succes.setMessage(gson.toJson(address));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        Address address = null;
        try {
            address = (Address) SQL.findById(Address.class, Integer.parseInt(resource));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
        if (address != null) {
            try {
                address.setAddrNo(request.getParameter("ADDR_NO"));
                address.setAddrAdd(request.getParameter("ADDR_ADD"));
                address.setStreet(request.getParameter("STREET"));
                address.setSubDistrictId(Integer.parseInt(request.getParameter("SUDT_ID")));
                address.update();
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

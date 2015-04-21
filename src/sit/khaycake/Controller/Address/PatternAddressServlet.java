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

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternAddressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));

            try {
                int result = Address.delete(Integer.parseInt(resource));
                if (result < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else if(resource.indexOf("subDistrict") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            try {
                Address address = (Address)SQL.findById(Address.class, Integer.parseInt(resource));
                if (address !=null) {
                    Gson gson = new Gson();
                    response.getWriter().print(gson.toJson(address.getSubDistrict()));
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }else{
            Address address = null;
            try {
                address = (Address) SQL.findById(Address.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (address != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(address));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Address address = null;
        try {
            address = (Address) SQL.findById(Address.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (address != null) {
            try {
                address.setAddrNo(request.getParameter("ADDR_NO"));
                address.setAddrAdd(request.getParameter("ADDR_ADD"));
                address.setStreet(request.getParameter("STREET"));
                address.setSubDistrict((SubDistrict)
                        SQL.findById(SubDistrict.class,request.getParameter("SUDT_ID")));
                address.update();
            }catch (Exception ex){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

package sit.khaycake.Controller.Address;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;

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
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(Address.TABLE_NAME)
                        .where(Address.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
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
            address.setAddrNo(request.getParameter("addrNo"));
            address.setAddrAdd(request.getParameter("addrAdd"));
            address.setStreet(request.getParameter("street"));

            SQL sql = new SQL();
            try {
                sql
                        .update(Address.TABLE_NAME)
                        .set(Address.COLUMN_ADDR_NO, address.getAddrNo())
                        .set(Address.COLUMN_ADDR_ADD, address.getAddrAdd())
                        .set(Address.COLUMN_STREET, address.getStreet())
                        .set(Address.COLUMN_SUB_DISTRICT_ID, request.getParameter("subDistrictId"))
                        .where(Address.COLUMN_ID, SQL.WhereClause.Operator.EQ, address.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

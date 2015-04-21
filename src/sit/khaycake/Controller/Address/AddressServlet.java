package sit.khaycake.Controller.Address;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;

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
            List addresses = SQL.findAll(Address.class);
            Gson gson = new Gson();
            String result = gson.toJson(addresses, Address.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Address address = new Address();
            address.setAddrNo(request.getParameter("addrNo"));
            address.setAddrAdd(request.getParameter("addrAdd"));
            address.setStreet(request.getParameter("street"));

            int addId = sql
                    .insert()
                    .into(Address.TABLE_NAME, Address.COLUMN_ADDR_ADD, Address.COLUMN_ADDR_NO, Address.COLUMN_STREET,
                            Address.COLUMN_SUB_DISTRICT_ID)
                    .values(address.getAddrAdd(), address.getAddrNo(), address.getStreet(),
                            Integer.parseInt(request.getParameter("subDistrictId")))
                    .exec();
            sql.clear();
            address.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(address));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }
}

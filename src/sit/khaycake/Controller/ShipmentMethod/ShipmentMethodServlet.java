package sit.khaycake.Controller.ShipmentMethod;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ShipmentMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ShipmentMethodServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List shipmentMethods = SQL.findAll(ShipmentMethod.class);
            Gson gson = new Gson();
            String result = gson.toJson(shipmentMethods, ShipmentMethod.class);
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            ShipmentMethod shipmentMethod = new ShipmentMethod();
            shipmentMethod.setName(request.getParameter("name"));
            shipmentMethod.setPrice(Double.parseDouble(request.getParameter("price")));
            shipmentMethod.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(shipmentMethod));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/
}

package sit.khaycake.Controller.ShipmentMethod;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ShipmentMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternShipmentMethodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            /*resource = resource.substring(0,resource.indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = ShipmentMethod.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            ShipmentMethod shipmentMethod = null;
            try {
                shipmentMethod = (ShipmentMethod) SQL.findById(ShipmentMethod.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (shipmentMethod != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(shipmentMethod));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        ShipmentMethod shipmentMethod = null;
        try {
            shipmentMethod = (ShipmentMethod) SQL.findById(ShipmentMethod.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (shipmentMethod != null) {
            shipmentMethod.setName(request.getParameter("name"));
            shipmentMethod.setPrice(Double.parseDouble(request.getParameter("price")));


            SQL sql = new SQL();
            try {
                sql
                        .update(ShipmentMethod.TABLE_NAME)
                        .set(ShipmentMethod.COLUMN_NAME, shipmentMethod.getName())
                        .set(ShipmentMethod.COLUMN_PRICE, shipmentMethod.getPrice())
                        .where(ShipmentMethod.COLUMN_ID, SQL.WhereClause.Operator.EQ, shipmentMethod.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

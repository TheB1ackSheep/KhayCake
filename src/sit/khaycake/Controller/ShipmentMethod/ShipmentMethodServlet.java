package sit.khaycake.Controller.ShipmentMethod;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.ShipmentMethod;
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
public class ShipmentMethodServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {
            succes.setMessage((ShipmentMethod) SQL.findAll(ShipmentMethod.class));
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
            ShipmentMethod shipmentMethod = new ShipmentMethod();
            shipmentMethod.setName(request.getParameter("NAME"));
            shipmentMethod.setPrice(Double.parseDouble(request.getParameter("PRICE")));
            shipmentMethod.save();

            succes.setMessage(shipmentMethod);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

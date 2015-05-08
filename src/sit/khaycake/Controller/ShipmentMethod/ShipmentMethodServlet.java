package sit.khaycake.Controller.ShipmentMethod;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Cart;
import sit.khaycake.model.Customer;
import sit.khaycake.model.ShipmentAddress;
import sit.khaycake.model.ShipmentMethod;
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
public class ShipmentMethodServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String[] resources = req.getRequestURI().split("/");

        String id = null, method = null;
        if (resources.length >= 5)
            id = resources[4];
        if (resources.length >= 6)
            method = resources[5];
        try {

            if (id == null) {
                //request URL is /shipment/method
                success.setMessage(SQL.findAll(ShipmentMethod.class));
            } else {
                //request URL is /shipment/method/*
                ShipmentMethod shipmentMethod = SQL.findById(ShipmentMethod.class, id);
                if (shipmentMethod != null)
                    success.setMessage(shipmentMethod);
                else
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }



        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {

            if(req.getParameter("shme_id") != null){
                Cart cart = Cart.getCart(session);
                if(cart != null)
                    cart.setShipmentMethod(SQL.findById(ShipmentMethod.class, req.getParameter("shme_id")));
                session.setAttribute("cart",cart);
                success.setMessage(cart);
            }else{

            }

        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }

    }
}

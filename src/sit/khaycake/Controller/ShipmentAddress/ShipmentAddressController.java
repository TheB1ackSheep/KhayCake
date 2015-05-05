package sit.khaycake.Controller.ShipmentAddress;

import sit.khaycake.Filter.request.ShipmentAddressRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Customer;
import sit.khaycake.model.ShipmentAddress;
import sit.khaycake.model.Tumbon;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/6/2015.
 */
@WebServlet(name = "ShipmentAddressController", urlPatterns = "/shipment/address/*")
public class ShipmentAddressController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            String shad_id = req.getParameter("shad_id");
            String fname = req.getParameter("fname");
            String lname = req.getParameter("lname");
            String address = req.getParameter("address");
            String tumb_id = req.getParameter("tumb_id");

            Customer user = (Customer) session.getAttribute("user");

            if (user != null) {

                if(shad_id.equals("-1")) {
                    boolean isNew = (id == null);
                    ShipmentAddress s = !isNew ? SQL.findById(ShipmentAddress.class, id) : new ShipmentAddress();
                    if (isNew && s == null)
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    else {
                        s.setFirstName(fname);
                        s.setLastName(lname);
                        s.setAddress(address);
                        s.setTumbon(SQL.findById(Tumbon.class, tumb_id));
                        s.setCustomer(user);
                        s.save(isNew);
                    }
                    success.setMessage(s);
                }else{
                    ShipmentAddress s = SQL.findById(ShipmentAddress.class, shad_id);
                    success.setMessage(s);
                }

            } else {
                error.setMessage("คุณยังไม่ได้ล็อกอิน");
            }


        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            Customer user = (Customer) session.getAttribute("user");

            if(user != null){
                if (id == null) {
                    //request URL is /shipment/address
                    success.setMessage(user.getAddresses());
                } else {
                    //request URL is /shipment/address/*
                    ShipmentAddress shipmentAddress = user.getAddress(id);
                    if (shipmentAddress != null)
                        success.setMessage(shipmentAddress);
                    else
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }else{
                error.setMessage("คุณยังไม่ได้ล็อกอิน");
            }



        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
    }
}

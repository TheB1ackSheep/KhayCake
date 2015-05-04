package sit.khaycake.Controller.OrderStatus;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.OrderStatusRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.OrderStatus;
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
public class OrderStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((OrderStatus) SQL.findAll(OrderStatus.class));
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
        OrderStatusRequest orderStatusRequest = new OrderStatusRequest(request);
        if(orderStatusRequest.validate()) {
            try {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setName(request.getParameter("name"));
                orderStatus.save();

                succes.setMessage(orderStatus);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

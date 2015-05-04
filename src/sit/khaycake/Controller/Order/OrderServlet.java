package sit.khaycake.Controller.Order;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.OrderRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Customer;
import sit.khaycake.model.OrderStatus;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.model.Order;
import sit.khaycake.model.Order.Status;
import sit.khaycake.model.Order.ShipMethod;
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
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((Order) SQL.findAll(Order.class));
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
        OrderRequest orderRequest = new OrderRequest(request);
        if(orderRequest.validate()) {
            try {
                Order order = new Order();
                order.setCustomer((Customer) SQL.findById(
                        Customer.class, Integer.parseInt(request.getParameter("cust_id"))));
                order.setOrderDate(AssisDateTime.Date(request.getParameter("order_date")));
                order.setStatus(Status.getStatus(Integer.parseInt(request.getParameter("orst_id"))));
                order.setShipMethod(ShipMethod.getShipMethod(Integer.parseInt(request.getParameter("shme_id"))));
                order.setShtrId(request.getParameter("shtr_id"));
                order.setTotalPrice(Double.parseDouble(request.getParameter("total_price")));
                order.setTotalQty(Integer.parseInt(request.getParameter("total_qty")));
                order.save();

                succes.setMessage(order);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

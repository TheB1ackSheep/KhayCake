package sit.khaycake.Controller.OrderItem;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Order;
import sit.khaycake.model.OrderItem;
import sit.khaycake.model.ProductSale;
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
public class OrderItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((OrderItem) SQL.findAll(OrderItem.class));
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

            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(Double.parseDouble(request.getParameter("AMOUNT")));
            orderItem.setOrder((Order) SQL.findById(
                    Order.class,Integer.parseInt(request.getParameter("ORDER_ID"))));
            orderItem.setPriceUnit(Double.parseDouble(request.getParameter("PRICE_UNIT")));
            orderItem.setProductSale((ProductSale)SQL.findById(
                    ProductSale.class,Integer.parseInt(request.getParameter("PRSA_ID"))));
            orderItem.setQty(Integer.parseInt(request.getParameter("QTY")));
            orderItem.save();

            succes.setMessage(orderItem);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

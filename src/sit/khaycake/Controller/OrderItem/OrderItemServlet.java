package sit.khaycake.Controller.OrderItem;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Order;
import sit.khaycake.model.OrderItem;
import sit.khaycake.model.ProductSale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class OrderItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(OrderItem.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(orderItem));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

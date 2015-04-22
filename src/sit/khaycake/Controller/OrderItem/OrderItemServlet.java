package sit.khaycake.Controller.OrderItem;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.OrderItem;

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
            List orderItems = SQL.findAll(OrderItem.class);
            Gson gson = new Gson();
            String result = gson.toJson(orderItems, OrderItem.class);
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(Double.parseDouble(request.getParameter("amount")));
            orderItem.setOrderId(Integer.parseInt(request.getParameter("odrerId")));
            orderItem.setPriceUnit(Double.parseDouble(request.getParameter("priceUnit")));
            orderItem.setPrsaId(Integer.parseInt(request.getParameter("prsaId")));
            orderItem.setQty(Integer.parseInt(request.getParameter("qty")));
            orderItem.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(orderItem));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

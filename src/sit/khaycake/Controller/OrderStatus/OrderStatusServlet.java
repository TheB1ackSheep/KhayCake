package sit.khaycake.Controller.OrderStatus;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class OrderStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(OrderStatus.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setName(request.getParameter("name"));
            orderStatus.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(orderStatus));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/
}

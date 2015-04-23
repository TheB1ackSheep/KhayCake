package sit.khaycake.Controller.OrderItem;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternOrderItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = OrderItem.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            OrderItem orderItem = null;
            try {
                orderItem = (OrderItem) SQL.findById(OrderItem.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (orderItem != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(orderItem));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 1)+1);
        OrderItem orderItem = null;
        try {
            orderItem = (OrderItem) SQL.findById(OrderItem.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (orderItem != null) {
            try{
                orderItem.setAmount(Double.parseDouble(request.getParameter("amount")));
                orderItem.setOrderId(Integer.parseInt(request.getParameter("odrerId")));
                orderItem.setPriceUnit(Double.parseDouble(request.getParameter("priceUnit")));
                orderItem.setPrsaId(Integer.parseInt(request.getParameter("prsaId")));
                orderItem.setQty(Integer.parseInt(request.getParameter("qty")));
                orderItem.update();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

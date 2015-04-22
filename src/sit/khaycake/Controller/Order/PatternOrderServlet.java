package sit.khaycake.Controller.Order;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.model.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                int a = Order.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            Order order = null;
            try {
                order = (Order) SQL.findById(Order.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (order != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(order));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);
        Order order = null;
        try {
            order = (Order) SQL.findById(Order.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (order != null) {
            try{
            order.setCustId(Integer.parseInt(request.getParameter("cusId")));
            order.setOrderDate(AssisDateTime.Date(request.getParameter("orderDate")));
            order.setOrstId(Integer.parseInt(request.getParameter("orstId")));
            order.setShmeId(Integer.parseInt(request.getParameter("shmeId")));
            order.setShtrId(request.getParameter("shtrId"));
            order.setTotalPrice(Double.parseDouble(request.getParameter("totalPrice")));
            order.setTotalQty(Integer.parseInt(request.getParameter("totalQty")));
            order.update();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);

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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        Order order = null;
        try {
            order = (Order) SQL.findById(Order.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (order != null) {
            try{
                order.setCustId(Integer.parseInt(request.getParameter("CUST_ID")));
                order.setOrderDate(AssisDateTime.Date(request.getParameter("ORDER_DATE")));
                order.setOrstId(Integer.parseInt(request.getParameter("ORST_ID")));
                order.setShmeId(Integer.parseInt(request.getParameter("SHME_ID")));
                order.setShtrId(request.getParameter("SHTR_ID"));
                order.setTotalPrice(Double.parseDouble(request.getParameter("TOTAL_PRICE")));
                order.setTotalQty(Integer.parseInt(request.getParameter("TOTAL_QTY")));
            order.update();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

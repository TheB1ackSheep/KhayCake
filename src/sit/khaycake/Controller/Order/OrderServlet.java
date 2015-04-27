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
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(Order.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Order order = new Order();
            order.setCustId(Integer.parseInt(request.getParameter("CUST_ID")));
            order.setOrderDate(AssisDateTime.Date(request.getParameter("ORDER_DATE")));
            order.setOrstId(Integer.parseInt(request.getParameter("ORST_ID")));
            order.setShmeId(Integer.parseInt(request.getParameter("SHME_ID")));
            order.setShtrId(request.getParameter("SHTR_ID"));
            order.setTotalPrice(Double.parseDouble(request.getParameter("TOTAL_PRICE")));
            order.setTotalQty(Integer.parseInt(request.getParameter("TOTAL_QTY")));
            order.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(order));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}

package sit.khaycake.Controller.Order;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Assis.AssisDateTime;
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
            List orders = SQL.findAll(Order.class);
            Gson gson = new Gson();
            String result = gson.toJson(orders, Order.class);
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
            order.setCustId(Integer.parseInt(request.getParameter("cusId")));
            order.setOrderDate(AssisDateTime.Date(request.getParameter("orderDate")));
            order.setOrstId(Integer.parseInt(request.getParameter("orstId")));
            order.setShmeId(Integer.parseInt(request.getParameter("shmeId")));
            order.setShtrId(request.getParameter("shtrId"));
            order.setTotalPrice(Double.parseDouble(request.getParameter("totalPrice")));
            order.setTotalQty(Integer.parseInt(request.getParameter("totalQty")));


            int addId = sql
                    .insert()
                    .into(Order.TABLE_NAME, Order.COLUMN_CUST_ID, Order.COLUMN_ORDER_DATE, Order.COLUMN_ORST_ID, Order.COLUMN_SHME_ID,
                            Order.COLUMN_SHTR_ID, Order.COLUMN_TOTAL_PRICE, Order.COLUMN_TOTAL_QTY)
                    .values(order.getCustId(), order.getOrderDate(), order.getOrstId(), order.getShmeId(), order.getShtrId(),
                            order.getTotalPrice(), order.getTotalQty())
                    .exec();
            sql.clear();
            order.setOrderId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(order));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

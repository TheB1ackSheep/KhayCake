package sit.khaycake.Controller.OrderItem;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Assis.AssisDateTime;
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
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(OrderItem.TABLE_NAME)
                        .where(OrderItem.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
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
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        OrderItem orderItem = null;
        try {
            orderItem = (OrderItem) SQL.findById(OrderItem.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (orderItem != null) {
            orderItem.setAmount(Double.parseDouble(request.getParameter("amount")));
            orderItem.setOrderId(Integer.parseInt(request.getParameter("odrerId")));
            orderItem.setPriceUnit(Double.parseDouble(request.getParameter("priceUnit")));
            orderItem.setPrsaId(Integer.parseInt(request.getParameter("prsaId")));
            orderItem.setQty(Integer.parseInt(request.getParameter("qty")));

            SQL sql = new SQL();
            try {
                sql
                        .update(OrderItem.TABLE_NAME)
                        .set(OrderItem.COLUMN_AMOUNT, orderItem.getAmount())
                        .set(OrderItem.COLUMN_ORDER_ID, orderItem.getOrderId())
                        .set(OrderItem.COLUMN_PRICE_UNIT, orderItem.getPriceUnit())
                        .set(OrderItem.COLUMN_PRSA_ID, orderItem.getPrsaId())
                        .set(OrderItem.COLUMN_QTY, orderItem.getQty())
                        .where(OrderItem.COLUMN_ID, SQL.WhereClause.Operator.EQ, orderItem.getOritId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

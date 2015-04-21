package sit.khaycake.Controller.Payment;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Assis.AssisDateTime;
import sit.khaycake.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List payments = SQL.findAll(Payment.class);
            Gson gson = new Gson();
            String result = gson.toJson(payments, Payment.class);
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
            Payment payment = new Payment();
            payment.setOrderId(Integer.parseInt(request.getParameter("orderId")));
            payment.setAmount(Double.parseDouble(request.getParameter("amount")));
            payment.setBaacId(Integer.parseInt(request.getParameter("baccId")));
            payment.setDateTime(AssisDateTime.DateTime(request.getParameter("dateTime")));
            payment.setPastId(Integer.parseInt(request.getParameter("pastId")));


            int addId = sql
                    .insert()
                    .into(Payment.TABLE_NAME, Payment.COLUMN_ORDER_ID, Payment.COLUMN_AMOUNT, Payment.COLUMN_BAAC_ID,
                            Payment.COLUMN_DATE_TIME, Payment.COLUMN_PAST_ID)
                    .values(payment.getOrderId(), payment.getAmount(), payment.getBaacId(), payment.getDateTime(), payment.getPastId())
                    .exec();
            sql.clear();
            payment.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(payment));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

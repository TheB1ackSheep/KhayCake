package sit.khaycake.Controller.Payment;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.util.AssisDateTime;
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
            Payment payment = new Payment();
            payment.setOrderId(Integer.parseInt(request.getParameter("orderId")));
            payment.setAmount(Double.parseDouble(request.getParameter("amount")));
            payment.setBaacId(Integer.parseInt(request.getParameter("baccId")));
            payment.setDateTime(AssisDateTime.DateTime(request.getParameter("dateTime")));
            payment.setPastId(Integer.parseInt(request.getParameter("pastId")));
            payment.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(payment));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

package sit.khaycake.Controller.Payment;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.BankAccount;
import sit.khaycake.model.Order;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = Payment.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            Payment payment = null;
            try {
                payment = (Payment) SQL.findById(Payment.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (payment != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(payment));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Payment payment = null;
        try {
            payment = (Payment) SQL.findById(Payment.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (payment != null) {
            try{
                payment.setOrder((Order) SQL.findById(Order.class, Integer.parseInt(request.getParameter("orderId"))));
                payment.setAmount(Double.parseDouble(request.getParameter("amount")));
                payment.setBaac((BankAccount)SQL.findById(BankAccount.class,Integer.parseInt(request.getParameter("baccId"))));
                payment.setDateTime(AssisDateTime.DateTime(request.getParameter("dateTime")));
                payment.setPastId(Integer.parseInt(request.getParameter("pastId")));
                payment.update();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

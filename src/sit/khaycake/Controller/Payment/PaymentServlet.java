package sit.khaycake.Controller.Payment;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.BankAccount;
import sit.khaycake.model.Order;
import sit.khaycake.model.PaymentStatus;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.model.Payment;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((Payment) SQL.findAll(Payment.class));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Payment payment = new Payment();
            payment.setOrder((Order) SQL.findById(Order.class, Integer.parseInt(request.getParameter("ORDER_ID"))));
            payment.setAmount(Double.parseDouble(request.getParameter("AMOUNT")));
            payment.setBaac((BankAccount) SQL.findById(BankAccount.class, Integer.parseInt(request.getParameter("BAAC_ID"))));
            payment.setDateTime(AssisDateTime.DateTime(request.getParameter("DATE_TIME")));
            payment.setPast(Payment.Status.getStatus(Integer.parseInt(request.getParameter("PAST_ID"))));
            payment.save();

            succes.setMessage(payment);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

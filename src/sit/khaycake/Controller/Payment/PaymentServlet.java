package sit.khaycake.Controller.Payment;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.*;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {

            String keyword = request.getParameter("q");
            if(keyword == null)
                success.setMessage(Payment.findAll());
            else
                success.setMessage(Payment.findByKeyword(keyword.split("\\s")));

        } catch (Exception ex) {
           error.setMessage(ex.getMessage());
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        Customer user = Customer.getCustomer(session);

        try {
            if(user != null) {
                Order order = SQL.findById(Order.class,request.getParameter("order_id"));
                if(order != null) {
                    Payment payment = new Payment();
                    payment.setOrder(order);
                    payment.setAmount(Double.parseDouble(request.getParameter("amount")));
                    payment.setBankAccount(SQL.findById(BankAccount.class, request.getParameter("bankacc_id")));
                    String dateTime = request.getParameter("date")+" "+request.getParameter("time");
                    Date date = new Date((new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateTime).getTime()));
                    payment.setDateTime(new Timestamp(date.getTime()));
                    payment.setStatus(SQL.findById(PaymentStatus.class, 1));
                    payment.save();

                    order.setStatus(SQL.findById(OrderStatus.class, 5));
                    order.update();

                    success.setMessage(payment);
                }else
                    success.setMessage("ไม่พบรายการสั่งซื้อ");
            }else{
                success.setMessage(403);
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

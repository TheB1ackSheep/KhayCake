package sit.khaycake.Controller.Payment;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Order;
import sit.khaycake.model.OrderStatus;
import sit.khaycake.model.Payment;
import sit.khaycake.model.PaymentStatus;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {

            String[] resources = request.getRequestURI().split("/");
            String id= null, method = null;
            if (resources.length >= 4)
                id = resources[3];
            if (resources.length >= 5)
                method = resources[4];

            if(id != null){
                if(Util.isInteger(id)){
                   Payment payment = SQL.findById(Payment.class, id);

                    if (payment != null) {
                        if (method == null)
                            success.setMessage(payment);
                        else {
                            if(method.equals("accept")){
                                payment.setStatus(SQL.findById(PaymentStatus.class, 2));
                                payment.update();
                                Order order = payment.getOrder();
                                order.setStatus(SQL.findById(OrderStatus.class, 2));
                                order.update();
                            }else if(method.equals("deny")){
                                payment.setStatus(SQL.findById(PaymentStatus.class, 3));
                                payment.update();
                                Order order = payment.getOrder();
                                if(order != null && order.getPayments() == null) {
                                    order.setStatus(SQL.findById(OrderStatus.class, 1));
                                    order.update();
                                }
                            }
                            success.setMessage(payment);
                        }
                    }else
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }else {
                    String keyword = request.getParameter("q");
                    if (id.equals("approving"))
                        success.setMessage(Payment.getApprovingPayment(keyword != null ? keyword.split("\\s") : null));
                    else if (id.equals("approved"))
                        success.setMessage(Payment.getApprovedPayment(keyword != null ? keyword.split("\\s"):null));
                    else if (id.equals("invalid"))
                        success.setMessage(Payment.getInvalidPayment(keyword!=null?keyword.split("\\s"):null));
                }
            }else{
                error.setMessage("Invalid Parameters");
            }



        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }
    }

}

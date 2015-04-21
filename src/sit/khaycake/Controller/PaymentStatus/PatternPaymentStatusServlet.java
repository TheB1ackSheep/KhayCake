package sit.khaycake.Controller.PaymentStatus;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.PaymentStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternPaymentStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(PaymentStatus.TABLE_NAME)
                        .where(PaymentStatus.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            PaymentStatus paymentStatus = null;
            try {
                paymentStatus = (PaymentStatus) SQL.findById(PaymentStatus.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (paymentStatus != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(paymentStatus));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = (PaymentStatus) SQL.findById(PaymentStatus.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (paymentStatus != null) {
            paymentStatus.setName(request.getParameter("name"));


            SQL sql = new SQL();
            try {
                sql
                        .update(PaymentStatus.TABLE_NAME)
                        .set(PaymentStatus.COLUMN_NAME, paymentStatus.getName())
                        .where(PaymentStatus.COLUMN_ID, SQL.WhereClause.Operator.EQ, paymentStatus.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}

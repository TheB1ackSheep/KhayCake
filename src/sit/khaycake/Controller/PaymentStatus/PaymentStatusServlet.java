package sit.khaycake.Controller.PaymentStatus;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.PaymentStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PaymentStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List paymentsStatuses = SQL.findAll(PaymentStatus.class);
            Gson gson = new Gson();
            String result = gson.toJson(paymentsStatuses, PaymentStatus.class);
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
            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setName(request.getParameter("name"));


            int addId = sql
                    .insert()
                    .into(PaymentStatus.TABLE_NAME, PaymentStatus.COLUMN_NAME)
                    .values(paymentStatus.getName())
                    .exec();
            sql.clear();
            paymentStatus.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(paymentStatus));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

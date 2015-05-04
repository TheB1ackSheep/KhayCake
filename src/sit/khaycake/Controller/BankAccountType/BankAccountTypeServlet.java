package sit.khaycake.Controller.BankAccountType;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.BankAccountType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class BankAccountTypeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(BankAccountType.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BankAccountType bankAccountType = new BankAccountType();
            bankAccountType.setName(request.getParameter("name"));
            bankAccountType.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(bankAccountType));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}

package sit.khaycake.Controller.BankAccount;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.BankAccount;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class BankAccountServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List bankAccounts = SQL.findAll(BankAccount.class);
            Gson gson = new Gson();
            String result = gson.toJson(bankAccounts, BankAccount.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccName(request.getParameter("accName"));
            bankAccount.setAccNo(request.getParameter("accNo"));
            bankAccount.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(bankAccount));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

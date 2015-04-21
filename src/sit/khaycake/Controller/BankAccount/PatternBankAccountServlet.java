package sit.khaycake.Controller.BankAccount;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.model.BankAccount;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternBankAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(BankAccount.TABLE_NAME)
                        .where(BankAccount.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            BankAccount bankAccount = null;
            try {
                bankAccount = (BankAccount) SQL.findById(BankAccount.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (bankAccount != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(bankAccount));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        BankAccount bankAccount = null;
        try {
            bankAccount = (BankAccount) SQL.findById(BankAccount.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (bankAccount != null) {
            bankAccount.setAccName(request.getParameter("accName"));
            bankAccount.setAccNo(request.getParameter("accNo"));

            SQL sql = new SQL();
            try {
                sql
                        .update(BankAccount.TABLE_NAME)
                        .set(BankAccount.COLUMN_ACC_NAME, bankAccount.getAccName())
                        .set(BankAccount.COLUMN_ACC_NO, bankAccount.getAccNo())
                        .set(BankAccount.COLUMN_BABR_ID, Integer.parseInt(request.getParameter("babrId")))
                        .set(BankAccount.COLUMN_BAAT_ID, Integer.parseInt(request.getParameter("baatId")))
                        .where(Bank.COLUMN_ID, SQL.WhereClause.Operator.EQ, bankAccount.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

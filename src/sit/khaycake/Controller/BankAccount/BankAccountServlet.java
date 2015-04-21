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
            SQL sql = new SQL();
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccName(request.getParameter("accName"));
            bankAccount.setAccNo(request.getParameter("accNo"));


            int addId = sql
                    .insert()
                    .into(BankAccount.TABLE_NAME, BankAccount.COLUMN_ACC_NAME, BankAccount.COLUMN_ACC_NO,
                            BankAccount.COLUMN_BABR_ID, BankAccount.COLUMN_BAAT_ID)
                    .values(bankAccount.getAccName(), bankAccount.getAccNo(), Integer.parseInt(request.getParameter("babrId")),
                            Integer.parseInt(request.getParameter("baatId")))
                    .exec();
            sql.clear();
            bankAccount.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(bankAccount));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }
}

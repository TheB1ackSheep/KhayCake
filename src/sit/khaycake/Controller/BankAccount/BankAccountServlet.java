package sit.khaycake.Controller.BankAccount;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.model.BankAccount;
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
public class BankAccountServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            BankAccount bankAccount = (BankAccount)SQL.findAll(BankAccount.class);
            success.setMessage(bankAccount);
        } catch (Exception ex) {
            error.setMessage(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setBranch((Bank.Branch)SQL.findById(Bank.Branch.class,request.getParameter("BABR_ID")));
            bankAccount.setAccName(request.getParameter("accName"));
            bankAccount.setAccNo(request.getParameter("accNo"));
            bankAccount.setType(BankAccount.Type.getType(Integer.parseInt(request.getParameter("BAAT_ID"))));
            bankAccount.save();

            Gson gson = new Gson();
            success.setMessage(bankAccount);
        } catch (Exception ex) {
            error.setMessage(ex);
        }

    }
}

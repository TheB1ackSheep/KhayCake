package sit.khaycake.Controller.BankAccount;

import sit.khaycake.Filter.request.BankAccountRequest;
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

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternBankAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                BankAccount bankAccount = (BankAccount)SQL.findById(BankAccount.class,resource);
                if (bankAccount != null) {
                    BankAccount.delete(Integer.parseInt(resource));
                    success.setMessage(bankAccount);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }


        } else {
            try {
                    BankAccount bankAccount = (BankAccount) SQL.findById(BankAccount.class, resource);
                    if (bankAccount != null) {
                        success.setMessage(bankAccount);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        BankAccountRequest bankAccountRequest = new BankAccountRequest(request);
        if(bankAccountRequest.validate()) {
            try {
                BankAccount bankAccount = (BankAccount) SQL.findById(BankAccount.class, resource);
                if (bankAccount != null) {
                    bankAccount.setBranch((Bank.Branch) SQL.findById(Bank.Branch.class, request.getParameter("babr_id")));
                    bankAccount.setAccName(request.getParameter("acc_name"));
                    bankAccount.setAccNo(request.getParameter("acc_no"));
                    bankAccount.setType(BankAccount.Type.getType(Integer.parseInt(request.getParameter("baat_id"))));
                    bankAccount.update();
                    success.setMessage(bankAccount);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }

}

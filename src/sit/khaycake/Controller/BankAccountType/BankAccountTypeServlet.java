package sit.khaycake.Controller.BankAccountType;


import sit.khaycake.database.SQL;
import sit.khaycake.model.BankAccountType;
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
public class BankAccountTypeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            BankAccountType bankAccountType = (BankAccountType)SQL.findAll(BankAccountType.class);
            success.setMessage(bankAccountType);
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
            BankAccountType bankAccountType = new BankAccountType();
            bankAccountType.setName(request.getParameter("name"));
            bankAccountType.save();

            success.setMessage(bankAccountType);
        } catch (Exception ex) {
            error.setMessage(ex);
        }

    }
}

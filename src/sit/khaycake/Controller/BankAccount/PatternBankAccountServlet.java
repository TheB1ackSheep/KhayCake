package sit.khaycake.Controller.BankAccount;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.model.BankAccount;
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
public class PatternBankAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] resources = request.getRequestURI().split("/");

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String id = null, method = null;
        if (resources.length >= 4)
            id = resources[3];
        if (resources.length >= 5)
            method = resources[4];

        try {
            BankAccount acc = SQL.findById(BankAccount.class, id);
            if(acc != null){
                success.setMessage(acc);
            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}

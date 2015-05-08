package sit.khaycake.Controller.BankAccountType;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
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
public class PatternBankAccountTypeServlet extends HttpServlet {
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

            BankAccountType type = SQL.findById(BankAccountType.class, id);
            if(type != null){

                    success.setMessage(type);
            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }
    }


}

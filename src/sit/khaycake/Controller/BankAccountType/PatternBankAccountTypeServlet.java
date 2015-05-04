package sit.khaycake.Controller.BankAccountType;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.BankAccountTypeRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.BankAccount;
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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                BankAccountType bankAccountType = (BankAccountType)SQL.findById(BankAccountType.class,resource);
                if (bankAccountType != null) {
                    BankAccountType.delete(Integer.parseInt(resource));
                    success.setMessage(bankAccountType);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            try {
                BankAccountType bankAccountType = (BankAccountType) SQL.findById(BankAccountType.class, resource);
                if (bankAccountType != null) {
                    success.setMessage(bankAccountType);
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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        BankAccountTypeRequest bankAccountTypeRequest = new BankAccountTypeRequest(request);
        if(bankAccountTypeRequest.validate()) {
            try {
                BankAccountType bankAccountType = (BankAccountType) SQL.findById(BankAccountType.class, resource);
                if (bankAccountType != null) {
                    bankAccountType.setName(request.getParameter("name"));
                    SQL sql = new SQL();
                    sql
                        .update(BankAccountType.TABLE_NAME)
                        .set(BankAccountType.COLUMN_NAME, bankAccountType.getName())
                        .where(BankAccountType.COLUMN_ID, SQL.WhereClause.Operator.EQ, bankAccountType.getId())
                        .exec();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }

    }

}

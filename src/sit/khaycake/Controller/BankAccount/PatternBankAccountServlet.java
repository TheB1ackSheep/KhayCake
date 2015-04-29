package sit.khaycake.Controller.BankAccount;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.model.BankAccount;
import sit.khaycake.util.Util;

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
        String resource = request.getRequestURI().substring(request.getPathInfo().indexOf("/", 0)+1);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                int a = BankAccount.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } else {
            BankAccount bankAccount = null;
            try {
                if(Util.isInteger(resource)) {
                    bankAccount = (BankAccount) SQL.findById(BankAccount.class, Integer.parseInt(resource));
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        BankAccount bankAccount = null;
        if(Util.isInteger(resource)) {
            try {
                bankAccount = (BankAccount) SQL.findById(BankAccount.class, Integer.parseInt(resource));
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            if (bankAccount != null) {
                try {
                    bankAccount.setBranch((Bank.Branch) SQL.findById(Bank.Branch.class, request.getParameter("BABR_ID")));
                    bankAccount.setAccName(request.getParameter("accName"));
                    bankAccount.setAccNo(request.getParameter("accNo"));
                    bankAccount.setType(BankAccount.Type.getType(Integer.parseInt(request.getParameter("BAAT_ID"))));
                    bankAccount.update();
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

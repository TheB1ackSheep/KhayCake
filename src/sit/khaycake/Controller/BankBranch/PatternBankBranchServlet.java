package sit.khaycake.Controller.BankBranch;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
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
public class PatternBankBranchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Bank.Branch bankBranch = (Bank.Branch)SQL.findById(Bank.Branch.class,resource);
                if (bankBranch != null) {
                    Bank.Branch.delete(Integer.parseInt(resource));
                    succes.setMessage(bankBranch);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            try {
                Bank.Branch bankBranch = (Bank.Branch) SQL.findById(Bank.Branch.class, resource);
                if (bankBranch != null) {
                    succes.setMessage(bankBranch);
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
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {
            Bank.Branch bankBranch = (Bank.Branch) SQL.findById(Bank.Branch.class, Integer.parseInt(resource));

            if (bankBranch != null) {
                bankBranch.setNameTh(request.getParameter("NAME_TH"));
                bankBranch.setNameEn(request.getParameter("NAME_EN"));
                bankBranch.setBank((Bank) SQL.findById(Bank.class, request.getParameter("BANK_ID")));
                bankBranch.update();
                succes.setMessage(bankBranch);
            } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }


    }

}

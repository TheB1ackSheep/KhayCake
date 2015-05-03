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
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class BankBranchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            List<Bank.Branch> bankBranchs = (List<Bank.Branch>)SQL.findAll(Bank.Branch.class);
            success.setMessage(bankBranchs);
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
            Bank.Branch bankBranch = new Bank.Branch();
            bankBranch.setNameTh(request.getParameter("NAME_TH"));
            bankBranch.setNameEn(request.getParameter("NAME_EN"));
            bankBranch.setBank((Bank)SQL.findById(Bank.class, request.getParameter("BANK_ID")));
            bankBranch.save();

            success.setMessage(bankBranch);
        } catch (Exception ex) {
            error.setMessage(ex);
        }

    }
}

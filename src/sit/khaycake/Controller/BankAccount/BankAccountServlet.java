package sit.khaycake.Controller.BankAccount;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.BankAccountRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.model.BankAccount;
import sit.khaycake.model.BankAccountType;
import sit.khaycake.model.BankBranch;
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
public class BankAccountServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String keyword = request.getParameter("q");

        try {
            if(keyword == null)
                success.setMessage(SQL.findAll(BankAccount.class));
            else
                success.setMessage(SQL.findByKeyword(BankAccount.class, keyword.split("\\s")));
        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        BankAccountRequest bankAccountRequest = new BankAccountRequest(request);
        if(bankAccountRequest.validate()){
            try{
                BankAccountType type = SQL.findById(BankAccountType.class, request.getParameter("type_id"));
                BankBranch branch = SQL.findById(BankBranch.class, request.getParameter("branch_id"));
                if(type == null)
                    error.setMessage("ไม่พบประเภทบัญชีนี้");
                else if(branch == null)
                    error.setMessage("ไม่พบสาขาธนาคารที่ระบุ");
                else {
                    BankAccount ba = new BankAccount();
                    ba.setAccName(request.getParameter("acc_name"));
                    ba.setAccNo(request.getParameter("acc_no"));
                    ba.setBranch(branch);
                    ba.setType(type);
                    ba.save();
                    success.setMessage(ba);
                }

            }catch (Exception ex){
                error.setMessage(ex.getMessage());
            }
        }
    }
}

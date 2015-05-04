package sit.khaycake.Controller.Bank;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.BankRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

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
public class PatternBankServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Bank bank = (Bank)SQL.findById(Bank.class,resource);
                if (bank != null) {
                    Bank.delete(Integer.parseInt(resource));
                    succes.setMessage(bank);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else if (resource.indexOf("branch") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    List<Bank.Branch> bankBranchs = Bank.Branch.findByBank(
                            Integer.parseInt(resource));
                    if (bankBranchs.isEmpty())
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(bankBranchs);
                }

            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        }else {
            try {
                Bank bank = (Bank) SQL.findById(Bank.class, resource);
                if (bank != null) {
                    succes.setMessage(bank);
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
        BankRequest bankRequest = new BankRequest(request);
        if(bankRequest.validate()) {
            try {
                Bank bank = (Bank) SQL.findById(Bank.class, Integer.parseInt(resource));

                if (bank != null) {
                    bank.setNameTh(request.getParameter("name_th"));
                    bank.setNameEn(request.getParameter("name_en"));
                    bank.update();
                    succes.setMessage(bank);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }

    }

}

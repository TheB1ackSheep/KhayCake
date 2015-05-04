package sit.khaycake.Controller.Bank;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.BankRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;
import sit.khaycake.model.PicBank;
import sit.khaycake.model.Picture;
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
public class BankServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            List<Bank> banks = (List<Bank>)SQL.findAll(Bank.class);
            success.setMessage(banks);
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
        BankRequest bankRequest = new BankRequest(request);
        if(bankRequest.validate()) {
            try {
                Bank bank = new Bank();
                bank.setNameTh(request.getParameter("name_th"));
                bank.setNameEn(request.getParameter("name_en"));
                bank.save();

                String[] pictures = request.getParameterValues("pic_id");
                for (String picStr : pictures) {
                    PicBank picBank = new PicBank();
                    Picture picture = (Picture) SQL.findById(Picture.class, picStr);
                    if (picture != null) {
                        picBank.setBankId(bank.getId());
                        picBank.setPicture(picture);
                        picBank.save();
                    }

                }

                success.setMessage(bank);
            } catch (Exception ex) {
                error.setMessage(ex);
            }
        }
    }
}

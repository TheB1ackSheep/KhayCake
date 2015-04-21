package sit.khaycake.Controller.Bank;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class BankServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List banks = SQL.findAll(Bank.class);
            Gson gson = new Gson();
            String result = gson.toJson(banks, Bank.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Bank bank = new Bank();
            bank.setName(request.getParameter("nameEn"));

            int addId = sql
                    .insert()
                    .into(Bank.TABLE_NAME, Bank.COLUMN_NAME_EN, Bank.COLUMN_NAME_TH)
                    .values(bank.getName(), request.getParameter("nameTh"))
                    .exec();
            sql.clear();
            bank.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(bank));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }
}

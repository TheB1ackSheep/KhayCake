package sit.khaycake.Controller.Branch;


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
public class BranchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List branchs = SQL.findAll(Bank.Branch.class);
            Gson gson = new Gson();
            String result = gson.toJson(branchs, Bank.Branch.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Bank.Branch branch = new Bank.Branch();
            branch.setName(request.getParameter("nameEn"));

            int addId = sql
                    .insert()
                    .into(Bank.Branch.TABLE_NAME, Bank.Branch.COLUMN_NAME_EN, Bank.Branch.COLUMN_NAME_TH,
                            Bank.Branch.COLUMN_BANK_ID)
                    .values(branch.getName(), request.getParameter("nameTh"), request.getParameter("BankId"))
                    .exec();
            sql.clear();
            branch.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(branch));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }
}

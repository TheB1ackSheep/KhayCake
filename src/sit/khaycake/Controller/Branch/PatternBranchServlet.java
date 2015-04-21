package sit.khaycake.Controller.Branch;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Bank;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternBranchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(Bank.Branch.TABLE_NAME)
                        .where(Bank.Branch.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            Bank.Branch branch = null;
            try {
                branch = (Bank.Branch) SQL.findById(Bank.Branch.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (branch != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(branch));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Bank.Branch branch = null;
        try {
            branch = (Bank.Branch) SQL.findById(Bank.Branch.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (branch != null) {
            branch.setName(request.getParameter("nameEn"));

            SQL sql = new SQL();
            try {
                sql
                        .update(Bank.Branch.TABLE_NAME)
                        .set(Bank.Branch.COLUMN_NAME_EN, branch.getName())
                        .set(Bank.Branch.COLUMN_NAME_TH, request.getParameter("nameTh"))
                        .set(Bank.Branch.COLUMN_BANK_ID, request.getParameter("bankId"))
                        .where(Bank.Branch.COLUMN_ID, SQL.WhereClause.Operator.EQ, branch.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

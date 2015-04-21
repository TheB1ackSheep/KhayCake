package sit.khaycake.Controller.Cutomer;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Assis.AssisDateTime;
import sit.khaycake.model.Assis.Encryption;
import sit.khaycake.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List customers = SQL.findAll(Customer.class);
            Gson gson = new Gson();
            String result = gson.toJson(customers, Customer.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Customer customer = new Customer();
            customer.setFname(request.getParameter("fname"));
            customer.setLname(request.getParameter("lname"));
            customer.setSex(request.getParameter("sex"));
            customer.setBirthday(AssisDateTime.Date("birthday"));
            customer.setPhone(request.getParameter("phone"));
            customer.setEmail(request.getParameter("email"));
            customer.setVatId(request.getParameter("vatId"));
            customer.setPwd(Encryption.md5("pwd"));
            SQL sql = new SQL();
            int cusId = sql
                    .insert()
                    .into(Customer.TABLE_NAME, Customer.COLUMN_FNAME, Customer.COLUMN_LNAME, Customer.COLUMN_SEX,
                            Customer.COLUMN_BOD, Customer.COLUMN_PHONE, Customer.COLUMN_EMAIL, Customer.COLUMN_VAT_ID,
                            Customer.COLUMN_PWD, Customer.COLUMN_ADDR_ID)
                    .values(customer.getFname(), customer.getLname(), customer.getSex(), customer.getBirthday(), customer.getPhone(),
                            customer.getEmail(), customer.getVatId(), customer.getPwd(), request.getParameter("addressId"))
                    .exec();
            sql.clear();
            customer.setId(cusId);
            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(customer));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

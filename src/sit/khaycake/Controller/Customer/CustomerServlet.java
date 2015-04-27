package sit.khaycake.Controller.Customer;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.Encryption;
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
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(Customer.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
            customer.save();
            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(customer));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}

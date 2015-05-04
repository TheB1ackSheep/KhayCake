package sit.khaycake.Controller.Customer;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.Encryption;
import sit.khaycake.model.Customer;
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
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((Customer) SQL.findAll(Customer.class));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Customer customer = new Customer();
            customer.setFname(request.getParameter("FNAME"));
            customer.setLname(request.getParameter("LNAME"));
            customer.setSex(request.getParameter("SEX"));
            customer.setBirthday(AssisDateTime.Date("DOB"));
            customer.setPhone(request.getParameter("PHONE"));
            customer.setEmail(request.getParameter("EMAIL"));
            customer.setVatId(request.getParameter("VAT_ID"));
            customer.setPwd(Encryption.md5("PWD"));
            customer.save();
            succes.setMessage(customer);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }
}

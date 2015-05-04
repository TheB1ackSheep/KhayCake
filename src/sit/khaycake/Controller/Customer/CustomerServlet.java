package sit.khaycake.Controller.Customer;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.CustomerRequest;
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
        CustomerRequest customerRequest = new CustomerRequest(request);
        if(customerRequest.validate()) {
            try {
                Customer customer = new Customer();
                customer.setFname(request.getParameter("fname"));
                customer.setLname(request.getParameter("lname"));
                customer.setSex(request.getParameter("sex"));
                customer.setBirthday(AssisDateTime.Date("dob"));
                customer.setPhone(request.getParameter("phone"));
                customer.setEmail(request.getParameter("email"));
                customer.setVatId(request.getParameter("vat_id"));
                customer.setPwd(Encryption.md5("pwd"));
                customer.save();
                succes.setMessage(customer);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

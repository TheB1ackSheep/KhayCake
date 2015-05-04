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

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Customer customer = (Customer)SQL.findById(Customer.class,resource);
                if (customer != null) {
                    Customer.delete(Integer.parseInt(resource));
                    succes.setMessage(customer);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                Customer customer = (Customer) SQL.findById(Customer.class, resource);
                if (customer != null) {
                    succes.setMessage(customer);
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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        CustomerRequest customerRequest = new CustomerRequest(request);
        if(customerRequest.validate()) {
            try {
                Customer customer = (Customer) SQL.findById(Customer.class, Integer.parseInt(resource));
                if (customer != null) {
                    customer.setFname(request.getParameter("fname"));
                    customer.setLname(request.getParameter("lname"));
                    customer.setSex(request.getParameter("sex"));
                    customer.setBirthday(AssisDateTime.Date("dob"));
                    customer.setPhone(request.getParameter("phone"));
                    customer.setEmail(request.getParameter("email"));
                    customer.setVatId(request.getParameter("vat_id"));
                    customer.setPwd(Encryption.md5("pwd"));
                    customer.update();
                    succes.setMessage(customer);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }

}

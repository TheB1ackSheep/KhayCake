package sit.khaycake.Controller.Authentication;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.AddressRequest;
import sit.khaycake.Filter.request.LoginRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
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
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(404);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        LoginRequest loginRequest = new LoginRequest(request);
        if(loginRequest.validate()) {
            try {
                Customer customer = Customer.authenticatoion(
                        request.getParameter("email"), request.getParameter("pwd"));
                if (customer != null) {
                    session.setAttribute("customer", customer);
                    success.setMessage(customer);
                } else {
                    error.setMessage("Email or Password is incorrect");
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

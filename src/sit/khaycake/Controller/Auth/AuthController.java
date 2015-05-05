package sit.khaycake.Controller.Auth;

import sit.khaycake.Filter.request.CustomerRequest;
import sit.khaycake.model.Customer;
import sit.khaycake.util.Encryption;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/5/2015.
 */
@WebServlet(name = "AuthController", urlPatterns = "/auth")
public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        Customer user = (Customer) session.getAttribute("user");
        if(user != null)
            success.setMessage(user);
        else
            success.setMessage("คุณยังไม่ได้ล็อกอิน");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        CustomerRequest customerRequest = new CustomerRequest(req);

        if (customerRequest.validate()) {
            try {
                String email = req.getParameter("email");
                String password = req.getParameter("pwd");
                String confirmPassword = req.getParameter("confirm-pwd");


                if (confirmPassword != null) {
                    //do register
                    if (password.equals(confirmPassword)) {
                        Customer cust = new Customer();
                        cust.setEmail(email);
                        cust.setPwd(Encryption.md5(password));
                        cust.save();
                        success.setMessage(cust);
                        session.setAttribute("user", cust);
                    } else {
                        error.setMessage("รหัสผ่านไม่ตรงกัน");
                    }

                } else {
                    //do login
                    Customer valid = Customer.auth(email, password);
                    if (valid != null) {
                        success.setMessage(valid);
                        session.setAttribute("user", valid);
                    } else {
                        success.setMessage("อีเมล์หรือรหัสผ่านไม่ถูกต้อง");
                    }
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

            if (error.getMessage() != null)
                session.removeAttribute("user");
        }

    }
}


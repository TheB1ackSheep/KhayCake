package sit.khaycake.Controller.Auth;

import sit.khaycake.Filter.request.CustomerRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Customer;
import sit.khaycake.model.Order;
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
@WebServlet(name = "AuthController", urlPatterns = "/auth/*")
public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] resources = req.getRequestURI().split("/");

        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        Customer user = Customer.getCustomer(session);

        String method = null,param = null,param2 = null;
        if (resources.length >= 4)
            method = resources[3];
        if (resources.length >= 5)
            param = resources[4];
        if (resources.length >= 6)
            param2 = resources[4];

        try {

            if (method == null) {

                if (user != null)
                    success.setMessage(user);
                else
                    success.setMessage(403);

            } else {


                if (user != null) {
                    if (method.equals("order")) {
                        if(param == null)
                            success.setMessage(user.getOrders());
                        else{
                            Order order = SQL.findById(Order.class, param);
                            if(order != null){
                                if(param2 == null)
                                    success.setMessage(order);
                                else
                                    success.setMessage(order.getItems());
                            }else
                                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        }

                    }else if(method.equals("payment")){
                        success.setMessage(user.getPayments());
                    }else if(method.equals("logout"))
                    {
                        session.removeAttribute("user");
                        success.setMessage("success");
                    }
                } else {
                    success.setMessage(403);
                }
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {

            CustomerRequest customerRequest = new CustomerRequest(req);

            if (customerRequest.validate()) {

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
                        success.setMessage("รหัสผ่านไม่ตรงกัน");
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


                if (error.getMessage() != null)
                    session.removeAttribute("user");
            }

        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }



    }
}


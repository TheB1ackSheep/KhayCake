package sit.khaycake.Controller.Customer;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Customer;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.Encryption;
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
        String[] resources = request.getRequestURI().split("/");

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String id = null, method = null;
        if (resources.length >= 4)
            id = resources[3];
        if (resources.length >= 5)
            method = resources[4];

        try {

            Customer customer = SQL.findById(Customer.class, id);

            if(customer != null){
                if(method == null){
                    success.setMessage(customer);
                }else{
                    if(method.equals("address")){
                        String sub = null;
                        if(resources.length >= 6)
                            sub = resources[5];
                        if(sub == null){
                            //show address
                            success.setMessage(customer.getAddresses());
                        }else{
                            //check sub
                            if(sub.equals("add")){
                                //TODO add address to customer

                            }else if(sub.equals("update")){
                                //TODO update address to customer
                            }
                        }
                    }
                }
            }else
                response.sendError(HttpServletResponse.SC_NOT_FOUND);

        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 0) + 1);
        Customer customer = null;
        try {
            customer = (Customer) SQL.findById(Customer.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (customer != null) {
            try {
                customer.setFname(request.getParameter("fname"));
                customer.setLname(request.getParameter("lname"));
                customer.setSex(request.getParameter("sex"));
                customer.setBirthday(AssisDateTime.Date("birthday"));
                customer.setPhone(request.getParameter("phone"));
                customer.setEmail(request.getParameter("email"));
                customer.setVatId(request.getParameter("vatId"));
                customer.setPwd(Encryption.md5("pwd"));
                customer.update();
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

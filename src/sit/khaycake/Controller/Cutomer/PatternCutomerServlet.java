package sit.khaycake.Controller.Cutomer;

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

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternCutomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));

        if (resource.indexOf("delete") >= 0) {
            resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            try {
                int a = Customer.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            Customer customer = null;
            try {
                customer = (Customer) SQL.findById(Customer.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (customer != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(customer));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Customer customer = null;
        try {
            customer = (Customer) SQL.findById(Customer.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (customer != null) {
            try{
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

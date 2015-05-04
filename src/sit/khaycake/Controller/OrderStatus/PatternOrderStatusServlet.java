package sit.khaycake.Controller.OrderStatus;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.OrderStatus;
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
public class PatternOrderStatusServlet extends HttpServlet {
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
                OrderStatus orderStatus = (OrderStatus)SQL.findById(OrderStatus.class,resource);
                if (orderStatus != null) {
                    OrderStatus.delete(Integer.parseInt(resource));
                    succes.setMessage(orderStatus);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            try {
                OrderStatus orderStatus = (OrderStatus) SQL.findById(OrderStatus.class, Integer.parseInt(resource));
                if (orderStatus != null) {
                    succes.setMessage(orderStatus);
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

        try {
            OrderStatus orderStatus = (OrderStatus) SQL.findById(OrderStatus.class, Integer.parseInt(resource));
            if (orderStatus != null) {
                orderStatus.setName(request.getParameter("name"));
                orderStatus.update();
                succes.setMessage(orderStatus);
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }


    }

}

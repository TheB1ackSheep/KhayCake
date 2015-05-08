package sit.khaycake.Controller.Order;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Customer;
import sit.khaycake.model.Order;
import sit.khaycake.model.OrderStatus;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {

            String[] resources = request.getRequestURI().split("/");
            String id= null, method = null;
            if (resources.length >= 4)
                id = resources[3];
            if (resources.length >= 5)
                method = resources[4];

            if(id != null){
                if(Util.isInteger(id)){
                    Order order = SQL.findById(Order.class, id);
                    if(method == null) {
                        if (order != null)
                            success.setMessage(order);
                        else
                            response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }else{
                        if(method.equals("item")){
                            success.setMessage(order.getItems());
                        }else if(method.equals("shipped")){
                            String track_id = request.getParameter("track_id");
                            if(track_id != null)
                                order.setShtrId(track_id);
                            order.setStatus(SQL.findById(OrderStatus.class, 3));
                            order.update();
                            success.setMessage(order);
                        }
                    }
                }else{
                    String keyword = request.getParameter("q");
                    if(id.equals("shipping")){
                        success.setMessage(Order.getShippingOrder(keyword != null ? keyword.split("\\s") : null));
                    }else if(id.equals("paid")){
                        success.setMessage(Order.getPaidOrder(keyword != null ? keyword.split("\\s") : null));
                    }
                }
            }else{
                error.setMessage("Invalid Parameters");
            }



        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }
    }



}

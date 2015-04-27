package sit.khaycake.Controller.Cart;

import com.google.gson.Gson;
import sit.khaycake.model.Address;
import sit.khaycake.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if(request.getSession(false).getAttribute("cart")!=null) {
                Cart cart = (Cart)request.getSession(false).getAttribute("cart");
                Gson gson = new Gson();
                String result = gson.toJson(cart);
                response.getWriter().print(result);
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}

package sit.khaycake.Controller.Cart;

import sit.khaycake.Filter.request.CartRequest;
import sit.khaycake.model.Cart;
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
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        success.setMessage(session.getAttribute("cart"));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CartRequest cartRequest = new CartRequest(request);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);


        try {
            String[] prodId = request.getParameterValues("p_id");
            String[] qty = request.getParameterValues("qty");

            Cart cart = new Cart();
            if (prodId != null && qty != null)
                for (int i = 0; i < prodId.length; i++)
                    cart.set(Integer.parseInt(prodId[i]), Integer.parseInt(qty[i]));
            session.setAttribute("cart", cart);
            success.setMessage(cart);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }



        /*if (request.getSession(false).getAttribute("cart") != null) {
            Cart cart = (Cart) request.getSession(false).getAttribute("cart");
            cart.add(Integer.parseInt(
                            resource.substring(0,resource.indexOf("/", 1))),
                    Integer.parseInt(resource.substring(resource.indexOf("/", 1)+1,
                            resource.indexOf("/", 2))));
            request.getSession().setAttribute("cart", cart);
        } else {
            Cart cart = new Cart();
            cart.add(Integer.parseInt(
                            resource.substring(0,resource.indexOf("/", 1))),
                    Integer.parseInt(resource.substring(resource.indexOf("/", 1)+1,
                            resource.indexOf("/", 2))));
            request.getSession().setAttribute("cart", cart);
        }*/
    }
}

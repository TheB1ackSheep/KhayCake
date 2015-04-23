package sit.khaycake.Controller.Cart;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
import sit.khaycake.model.Cart;
import sit.khaycake.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 2)+1);
        try {

            if (resource.indexOf("delete") >= 0) {
                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.remove(Integer.parseInt(resource.substring(0,resource.indexOf("/", 1))));
                    request.getSession(false).setAttribute("cart",cart);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if (resource.indexOf("add") >= 0) {
                if (request.getSession(false).getAttribute("cart") != null) {
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
                }

            } else if (resource.indexOf("reduce") >= 0) {
                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.reduce(Integer.parseInt(
                                    resource.substring(0, resource.indexOf("/", 1))),
                            Integer.parseInt(resource.substring(resource.indexOf("/", 1) + 1,
                                    resource.indexOf("/", 2))));
                    request.getSession(false).setAttribute("cart",cart);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }catch(Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);
        Address address = null;
        try {
            address = (Address) SQL.findById(Address.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (address != null) {
            try {
                address.setAddrNo(request.getParameter("ADDR_NO"));
                address.setAddrAdd(request.getParameter("ADDR_ADD"));
                address.setStreet(request.getParameter("STREET"));
                address.setSubDistrict((SubDistrict)
                        SQL.findById(SubDistrict.class,request.getParameter("SUDT_ID")));
                address.update();
            }catch (Exception ex){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }*/

}

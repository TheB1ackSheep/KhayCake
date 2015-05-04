package sit.khaycake.Controller.Cart;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Cart;
import sit.khaycake.model.Product;
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
public class PatternCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        if(resource.indexOf("add") >= 0) {
            try {
                Product product = (Product) SQL.findById(Product.class,  resource.substring(resource.indexOf("/")));
                int qty = Integer.parseInt(resource.substring(resource.indexOf("/") + 1,resource.indexOf("/",1)));
                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.add(product, qty);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                } else {
                    Cart cart = new Cart();
                    cart.add(product, qty);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }else if(resource.indexOf("reduce") >= 0) {
            try {
                Product product = (Product) SQL.findById(Product.class,  resource.substring(resource.indexOf("/")));
                int qty = Integer.parseInt(resource.substring(resource.indexOf("/") + 1, resource.indexOf("/", 1)));

                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.reduce(product, qty);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }else if(resource.indexOf("delete") >= 0) {
            try {
                Product product = (Product) SQL.findById(Product.class,  resource.substring(resource.indexOf("/")));

                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.remove(product);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        if(resource.indexOf("add") >= 0) {
            try {
                Product product = (Product) SQL.findById(Product.class, request.getParameter("PROD_ID"));
                int qty = Integer.parseInt(request.getParameter("QTY"));

                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.add(product, qty);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                } else {
                    Cart cart = new Cart();
                    cart.add(product, qty);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }else if(resource.indexOf("reduce") >= 0) {
            try {
                Product product = (Product) SQL.findById(Product.class, request.getParameter("PROD_ID"));
                int qty = Integer.parseInt(request.getParameter("QTY"));

                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.reduce(product, qty);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }else if(resource.indexOf("delete") >= 0) {
            try {
                Product product = (Product) SQL.findById(Product.class, request.getParameter("PROD_ID"));

                if (request.getSession(false).getAttribute("cart") != null) {
                    Cart cart = (Cart) request.getSession(false).getAttribute("cart");
                    cart.remove(product);
                    request.getSession().setAttribute("cart", cart);
                    succes.setMessage(cart);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }

}

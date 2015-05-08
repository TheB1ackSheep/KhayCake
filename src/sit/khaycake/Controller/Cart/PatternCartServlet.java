package sit.khaycake.Controller.Cart;

import sit.khaycake.Filter.request.CartRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.*;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {

            String[] resources = request.getRequestURI().split("/");
            String method = null;
            if (resources.length >= 4)
                method = resources[3];
            if (method != null) {
                if (method.equalsIgnoreCase("add")) {

                    CartRequest cartRequest = new CartRequest(request);
                    if (cartRequest.validate()) {
                        String pId = request.getParameter("p_id");
                        String qty = request.getParameter("qty");

                        Cart cart = Cart.getCart(session);
                        cart.add(Integer.parseInt(pId), Integer.parseInt(qty));
                        session.setAttribute("cart", cart);
                        success.setMessage(cart);
                    }

                }else if (method.equalsIgnoreCase("checkout")) {

                    Cart cart = Cart.getCart(session);
                    Customer cust = Customer.getCustomer(session);
                    if(cust != null && cart.getItems().size() > 0 && cart.getShipmentAddress() != null && cart.getShipmentMethod() != null){
                        Order order = new Order();
                        order.setCustomer(cust);
                        order.setOrderDate(new Date(System.currentTimeMillis()));
                        order.setShipMethod(cart.getShipmentMethod());
                        order.setShipAddress(cart.getShipmentAddress());
                        order.setStatus(SQL.findById(OrderStatus.class, 1));
                        order.setTotalPrice(cart.getTotalPrice()+cart.getShipmentMethod().getPrice());
                        order.setTotalQty(cart.getTotalQty());
                        order.save();

                        for(Cart.Item i : cart.getItems()){
                            OrderItem item = new OrderItem();
                            item.setOrder(order);
                            item.setAmount(i.getTotal());
                            item.setQty(i.getQty());
                            item.setProduct(i.getProduct());
                            item.save();
                        }
                        session.removeAttribute("cart");
                        success.setMessage(order);

                    }else{
                        error.setMessage("มีบางอย่างผิดพลาด");
                    }


                }
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

}

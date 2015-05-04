package sit.khaycake.Controller.Invoice;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Invoice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternInvoiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            try {
                int a =Invoice.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            Invoice invoice = null;
            try {
                invoice = (Invoice) SQL.findById(Invoice.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            if (invoice != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(invoice));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Invoice invoice = null;
        try {
            invoice = (Invoice) SQL.findById(Invoice.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (invoice != null) {
            try{
                invoice.setDate(AssisDateTime.Date(request.getParameter("date")));
                invoice.setQrandTotal(Double.parseDouble(request.getParameter("qrandTotal")));
                invoice.setSubTotal(Double.parseDouble(request.getParameter("subTotal")));
                invoice.setVat(Double.parseDouble(request.getParameter("vat")));
                invoice.setQrandTotalText(request.getParameter("qrandTotalText"));
                invoice.update();
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/

}

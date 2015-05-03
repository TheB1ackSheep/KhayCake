package sit.khaycake.Controller.Invoice;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.model.Invoice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class InvoiceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(SQL.findAll(Invoice.class));
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Invoice invoice = new Invoice();
            invoice.setDate(AssisDateTime.Date(request.getParameter("date")));
            invoice.setQrandTotal(Double.parseDouble(request.getParameter("qrandTotal")));
            invoice.setSubTotal(Double.parseDouble(request.getParameter("subTotal")));
            invoice.setVat(Double.parseDouble(request.getParameter("vat")));
            invoice.setQrandTotalText(request.getParameter("qrandTotalText"));
            invoice.save();

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(invoice));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/
}

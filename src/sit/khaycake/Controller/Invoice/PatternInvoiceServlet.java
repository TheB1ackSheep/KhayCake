package sit.khaycake.Controller.Invoice;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.InvoiceRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.MerchantInfo;
import sit.khaycake.model.Payment;
import sit.khaycake.util.AssisDateTime;
import sit.khaycake.model.Invoice;
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
public class PatternInvoiceServlet extends HttpServlet {
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
                Invoice invoice = (Invoice)SQL.findById(Invoice.class,resource);
                if (invoice != null) {
                    Invoice.delete(Integer.parseInt(resource));
                    succes.setMessage(invoice);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            try {
                Invoice invoice = (Invoice) SQL.findById(Invoice.class, Integer.parseInt(resource));
                if (invoice != null) {
                    succes.setMessage(invoice);
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
        InvoiceRequest invoiceRequest = new InvoiceRequest(request);
        if(invoiceRequest.validate()) {
            try {
                Invoice invoice = (Invoice) SQL.findById(Invoice.class, Integer.parseInt(resource));
                if (invoice != null) {
                    invoice.setDate(AssisDateTime.Date(request.getParameter("date")));
                    invoice.setQrandTotal(Double.parseDouble(request.getParameter("qrand_total")));
                    invoice.setSubTotal(Double.parseDouble(request.getParameter("sub_total")));
                    invoice.setVat(Double.parseDouble(request.getParameter("vat")));
                    invoice.setQrandTotalText(request.getParameter("qrand_total_text"));
                    invoice.setPayment((Payment) SQL.findById(Payment.class, Integer.parseInt(request.getParameter("patm_id"))));
                    invoice.setMerchantInfo((MerchantInfo)SQL.findById(MerchantInfo.class, Integer.parseInt(request.getParameter("meif_id"))));
                    invoice.update();
                    succes.setMessage(invoice);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }

    }

}

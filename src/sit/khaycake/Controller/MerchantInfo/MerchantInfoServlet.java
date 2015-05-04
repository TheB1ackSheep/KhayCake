package sit.khaycake.Controller.MerchantInfo;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.MerchantInfoRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
import sit.khaycake.model.MerchantInfo;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class MerchantInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            succes.setMessage((MerchantInfo) SQL.findAll(MerchantInfo.class));
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        MerchantInfoRequest merchantInfoRequest = new MerchantInfoRequest(request);
        if(merchantInfoRequest.validate()) {
            try {
                MerchantInfo merchantInfo = new MerchantInfo();
                merchantInfo.setName(request.getParameter("name"));
                merchantInfo.setVatId(request.getParameter("vat_id"));
                merchantInfo.setPhone(request.getParameter("phone"));
                merchantInfo.setFax(request.getParameter("fax"));
                merchantInfo.setVatValue(Double.parseDouble(request.getParameter("vat_value")));
                merchantInfo.setAddress((Address) SQL.findById(Address.class, request.getParameter("addr_id")));
                merchantInfo.save();
                succes.setMessage(merchantInfo);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }
}

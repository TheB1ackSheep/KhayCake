package sit.khaycake.Controller.MerchantInfo;

import com.google.gson.Gson;
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

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternMerchantInfoServlet extends HttpServlet {
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
                MerchantInfo merchantInfo = (MerchantInfo)SQL.findById(MerchantInfo.class,resource);
                if (merchantInfo != null) {
                    MerchantInfo.delete(Integer.parseInt(resource));
                    succes.setMessage(merchantInfo);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {

            try {
                MerchantInfo merchantInfo = (MerchantInfo) SQL.findById(MerchantInfo.class, resource);
                if (merchantInfo != null) {
                    succes.setMessage(merchantInfo);
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
        try {
            MerchantInfo merchantInfo = (MerchantInfo) SQL.findById(MerchantInfo.class, Integer.parseInt(resource));
            if (merchantInfo != null) {
                merchantInfo.setName(request.getParameter("NAME"));
                merchantInfo.setVatId(request.getParameter("VAT_ID"));
                merchantInfo.setPhone(request.getParameter("PHONE"));
                merchantInfo.setFax(request.getParameter("FAX"));
                merchantInfo.setVatValue(Double.parseDouble(request.getParameter("VAT_VALUE")));
                merchantInfo.setAddress((Address)SQL.findById(Address.class,request.getParameter("ADDR_ID")));
                merchantInfo.update();
                succes.setMessage(merchantInfo);
            } else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

}

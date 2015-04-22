package sit.khaycake.Controller.MerchantInfo;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.MerchantInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternMerchantInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);

        if (resource.indexOf("delete") >= 0) {
            /*resource = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/", 1));
            SQL sql = new SQL();
            try {
                int a = sql
                        .delete(MerchantInfo.TABLE_NAME)
                        .where(MerchantInfo.COLUMN_ID, SQL.WhereClause.Operator.EQ, resource)
                        .exec();
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }*/

        } else {
            MerchantInfo merchantInfo = null;
            try {
                merchantInfo = (MerchantInfo) SQL.findById(MerchantInfo.class, Integer.parseInt(resource));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            if (merchantInfo != null) {
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(merchantInfo));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        MerchantInfo merchantInfo = null;
        try {
            merchantInfo = (MerchantInfo) SQL.findById(MerchantInfo.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (merchantInfo != null) {
            merchantInfo.setName(request.getParameter("name"));
            merchantInfo.setVatId(request.getParameter("VatId"));
            merchantInfo.setPhone(request.getParameter("phone"));
            merchantInfo.setFax(request.getParameter("fax"));
            merchantInfo.setVatValue(Double.parseDouble(request.getParameter("vatValue")));

            SQL sql = new SQL();
            try {
                sql
                        .update(MerchantInfo.TABLE_NAME)
                        .set(MerchantInfo.COLUMN_NAME, merchantInfo.getName())
                        .set(MerchantInfo.COLUMN_VAT_ID, merchantInfo.getVatId())
                        .set(MerchantInfo.COLUMN_PHONE, merchantInfo.getPhone())
                        .set(MerchantInfo.COLUMN_FAX, merchantInfo.getFax())
                        .set(MerchantInfo.COLUMN_VAT_VALUE, merchantInfo.getVatValue())
                        .set(MerchantInfo.COLUMN_ADDR_ID, request.getParameter("addressId"))
                        .where(MerchantInfo.COLUMN_ID, SQL.WhereClause.Operator.EQ, merchantInfo.getId())
                        .exec();

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }*/

}
